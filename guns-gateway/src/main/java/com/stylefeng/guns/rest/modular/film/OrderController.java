package com.stylefeng.guns.rest.modular.film;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFieldT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.common.persistence.model.OrderVo;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.film.service.IMoocOrderTService;
import com.stylefeng.guns.rest.modular.film.service.IMtimeFieldTService;
import com.stylefeng.guns.rest.modular.film.service.MtimeUserService;
import com.stylefeng.guns.rest.modular.film.util.JedisAdapter;
import com.stylefeng.guns.rest.modular.film.util.ResponseVo;
import com.stylefeng.guns.rest.modular.film.util.StringToIntegerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by cute coder
 * 2019/6/9 18:52
 */
@RequestMapping("order")
@RestController
public class OrderController {
    @Reference
    MtimeUserService mtimeUserService;

    @Reference
    IMoocOrderTService iMoocOrderTService;

    @Reference
    IMtimeFieldTService iMtimeFieldTService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    JedisAdapter jedisAdapter;


    /*
     * 先校验soldSeats是否在区间里
     * 再检验是否均未出售
     * */
    @RequestMapping("buyTickets")
    public ResponseVo buyTickets(HttpServletRequest request, int fieldId, int[] soldSeats, String seatsName) {
        boolean isSoldSeatsCorrect = isSoldSeatsCorrect(fieldId, soldSeats);
        boolean isSoldSeatsLegal = isSoldSeatsLegal(fieldId, soldSeats);
        if (!isSoldSeatsCorrect || !isSoldSeatsLegal) {
            return ResponseVo.systemException();
        }
        OrderVo orderVo = generateOrder(request, fieldId, soldSeats);
       if (orderVo==null){
           ResponseVo responseVo = ResponseVo.businessException();
           responseVo.setMsg("订单生成失败");
           return responseVo;
       }else {
           ResponseVo responseVo = ResponseVo.ok();
           responseVo.setData(orderVo);
           return responseVo;

       }

    }

    private OrderVo generateOrder(HttpServletRequest request, int fieldId, int[] soldSeats) {
        String orderId = UUID.randomUUID().toString();
        MtimeFieldT mtimeFieldT = iMtimeFieldTService.selectById(fieldId);
        Integer cinemaId = mtimeFieldT.getCinemaId();
        int filmId = mtimeFieldT.getFilmId();
        int filmPrice = mtimeFieldT.getPrice();
        double orderPrice = 1.0 * soldSeats.length * filmPrice;
        Date orderTime = new Date();
        MtimeUserT userFromHeader = getUserFromHeader(request);
        Integer userId = userFromHeader.getUuid();
        MoocOrderT moocOrderT = new MoocOrderT();
        moocOrderT.setUuid(orderId);
        moocOrderT.setOrderStatus(0);
        moocOrderT.setCinemaId(cinemaId);
        moocOrderT.setFieldId(fieldId);
        moocOrderT.setFilmPrice((double) filmPrice);
        moocOrderT.setOrderTime(orderTime);
        moocOrderT.setOrderPrice(orderPrice);
        moocOrderT.setOrderUser(userId);
        moocOrderT.setFieldId(fieldId);
        moocOrderT.setFilmId(filmId);
        moocOrderT.setSeatsIds(soldSeats.toString());
        boolean insert = iMoocOrderTService.insert(moocOrderT);
        OrderVo orderVo =null;
        if (insert==true ) {
             orderVo = new OrderVo();
             orderVo.setSeatsName("");
        }
        return orderVo;
    }

    private boolean isSoldSeatsLegal(int fieldId, int[] soldSeats) {
        List<MoocOrderT> ordersByFieldId = iMoocOrderTService.selectList(
                new EntityWrapper<MoocOrderT>().eq("field_id", fieldId));
        if (ordersByFieldId == null) {
            return true;
        }
        boolean isSoldSeatsLegal = true;
        for (int i = 0; i < ordersByFieldId.size(); i++) {
            MoocOrderT order = ordersByFieldId.get(i);
            Integer[] array = StringToIntegerUtil.getArray(order.getSeatsIds());
            for (int j = 0; j < soldSeats.length; j++) {
                isSoldSeatsLegal = true;
                for (int k = 0; k < array.length; k++) {
                    if (soldSeats[j] == array[k]) {
                        isSoldSeatsLegal = false;
                    }
                }
            }
        }
        return isSoldSeatsLegal;
    }

    private boolean isSoldSeatsCorrect(int fieldId, int[] soldSeats) {
        String jsonStr = jedisAdapter.hget("hall_seats", "mtime_hall_dict_t");
        Map parse = (Map) JSONUtils.parse(jsonStr);
        String ids = (String) parse.get("ids");
        Integer[] array = StringToIntegerUtil.getArray(ids);
        boolean isSoldSeatsCorrect = false;
        for (int i = 0; i < soldSeats.length; i++) {

            isSoldSeatsCorrect = false;
            for (int j = 0; j < array.length; j++) {
                if (soldSeats[i] == array[j]) {
                    isSoldSeatsCorrect = true;
                }
            }
        }

        return isSoldSeatsCorrect;

    }

    @RequestMapping("getOrderInfo")
    public ResponseVo getOrderInfo(HttpServletRequest request) {
        MtimeUserT mtimeUserT = getUserFromHeader(request);
        List<MoocOrderT> ordersByUserId = iMoocOrderTService.selectList(
                new EntityWrapper<MoocOrderT>().eq("order_user", mtimeUserT.getUuid()));
        if (ordersByUserId == null) {
            ResponseVo responseVo = ResponseVo.businessException();
            responseVo.setMsg("订单列表为空哦 (-_-)");
            return responseVo;
        }
        ResponseVo responseVo = ResponseVo.ok();
        responseVo.setData(ordersByUserId);
        return responseVo;
    }

    private MtimeUserT getUserFromHeader(HttpServletRequest request) {

        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        MtimeUserT mtimeUserT = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            String usernameFromToken = jwtTokenUtil.getUsernameFromToken(authToken);
            List<MtimeUserT> users = mtimeUserService.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", usernameFromToken));
            mtimeUserT = users.get(0);
        }
        return mtimeUserT;

    }
}
