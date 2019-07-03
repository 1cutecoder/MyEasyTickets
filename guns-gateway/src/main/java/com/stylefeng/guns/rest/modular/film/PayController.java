package com.stylefeng.guns.rest.modular.film;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.demo.trade.MainPay;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.film.service.IMoocOrderTService;
import com.stylefeng.guns.rest.modular.film.service.IMtimeFieldTService;
import com.stylefeng.guns.rest.modular.film.service.MtimeUserService;
import com.stylefeng.guns.rest.modular.film.util.JedisAdapter;
import com.stylefeng.guns.rest.modular.film.util.ResponseVo;
import com.stylefeng.guns.rest.modular.film.util.StringToIntegerUtil;
import com.stylefeng.guns.rest.modular.film.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * Created by cute coder
 * 2019/6/9 18:52
 */
@RestController
public class PayController {
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

    /*获取从支付二维码*/
    @RequestMapping("/order/getPayInfo")
    public ResponseVO getPayInfo(HttpServletRequest request, String orderId) {
        try {
            List<MoocOrderT> orderTS = iMoocOrderTService.selectList(new EntityWrapper<MoocOrderT>().eq("UUID", orderId));
            if (orderTS == null) {
                ResponseVO responseVO = ResponseVO.appFail("订单支付失败，请稍后重试");
                return responseVO;
            }
            MainPay mainPay = new MainPay();
            String realPath = mainPay.test_trade_precreate();
            String fileUrl = oss(realPath);
            HashMap<String, Object> data = new HashMap<>();
            String endpoint ="oss-cn-hangzhou.aliyuncs.com";
            String imgPre = "http://cskanyanmall-file"+"."+endpoint+"/";
            data.put("orderId", orderId);
            data.put("QRCodeAddress",fileUrl);
            ResponseVO responseVO = ResponseVO.success(imgPre, data);
            return responseVO;
        } catch (IOException e) {
           return ResponseVO.appFail("系统出现异常，请联系管理员");
        }
    }

    @RequestMapping("/order/getPayResult")
    public ResponseVO getPayResult(MoocOrderT moocOrderT,int tryNums) {
        if (tryNums>=4) {
        return     ResponseVO.serviceFail("订单支付失败，请稍后重试");
        }
        HashMap<String, Object> data = new HashMap<>();
        MainPay mainPay = new MainPay();
        boolean test_trade_query = mainPay.test_trade_query();
        if (!test_trade_query) {
            ResponseVO responseVO = ResponseVO.appFail("系统出现异常，请联系管理员");
            return responseVO;
        }
        List<MoocOrderT> orderTS = iMoocOrderTService.selectList(new EntityWrapper<MoocOrderT>().eq("UUID", moocOrderT.getUuid()));
        if (orderTS == null) {
            ResponseVO responseVO = ResponseVO.appFail("系统出现异常，请联系管理员");
            return responseVO;
        }
        MoocOrderT orderT = orderTS.get(0);
        orderT.setOrderStatus(1);
        iMoocOrderTService.updateById(orderT);
        data.put("orderId",orderT.getUuid());
        data.put("orderStatus",1);
        data.put("orderMsg","支付成功");
        return ResponseVO.success(data);
    }
 
    public String oss (String realPath) throws IOException {
        HashMap<String, Object> vo = new HashMap<>();
        File file = new File(realPath);
        InputStream inputStream = new FileInputStream(file);
        String key = UUID.randomUUID().toString().replace("-","");
        String endpoint ="oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI51e4eZN7Xq11";
        String secretAccessKey = "nfIy9fZMOxLGj15E6MXEV78bjugFgG";
        String buckName="cskanyanmall-file";
        OSSClient ossClient = null;
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(buckName,key,inputStream);
            ossClient = new OSSClient(endpoint,accessKeyId,secretAccessKey);
            PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }
        String fileUrl = key;
        return fileUrl;
    }


}
