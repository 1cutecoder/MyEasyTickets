package com.stylefeng.guns.rest.modular.film;

 import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.common.persistence.model.ResponseUserModel;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.controller.dto.MtimeAuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.MtimeAuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.modular.film.service.MtimeUserService;
import com.stylefeng.guns.rest.modular.film.util.JedisAdapter;
import com.stylefeng.guns.rest.modular.film.util.ResponseVo;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by cute coder
 * 2019/6/5 11:03
 */
@RestController
public class MtimeUserController {

    @Reference
    MtimeUserService mtimeUserService;

    @RequestMapping("/user/register")
    public ResponseVo register(MtimeUserT mtimeUserT) {
        try {
            String userPwd = mtimeUserT.getPassword();
            MtimeUserT userForSelect = new MtimeUserT();
            userForSelect.setUsername(mtimeUserT.getUsername());
            MtimeUserT selectUser = mtimeUserService.selectOne(userForSelect);
            if (selectUser != null) {
                ResponseVo responseVo = ResponseVo.businessException();
                responseVo.setMsg("用户已存在");
                return responseVo;
            }
            String encrypt = MD5Util.encrypt(userPwd);
            mtimeUserT.setUpdateTime(new Date());
            mtimeUserT.setCreateTime(new Date());
            mtimeUserT.setUuid(UUID.randomUUID().toString().hashCode());
            mtimeUserT.setPassword(encrypt);
            int insert = mtimeUserService.insert(mtimeUserT);
            ResponseVo responseVo = null;
            if (insert != 1) {
                ResponseVo.systemException();
                return responseVo;
            } else if (insert == 1) {
                responseVo = ResponseVo.ok();
                responseVo.setMsg("注册成功");
                return responseVo;
            }
        } catch (Exception e) {
            return ResponseVo.systemException();
        }
        return ResponseVo.systemException();
    }


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "userValidator")
    private IReqValidator reqValidator;

    @Autowired
    JedisAdapter jedisAdapter;

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseVo createAuthenticationToken(AuthRequest authRequest) {

        try {
            boolean validate = reqValidator.validate(authRequest);
            if (validate) {
                final String randomKey = jwtTokenUtil.getRandomKey();
                final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
                long mtimeAuth = jedisAdapter.hset(randomKey, "MtimeAuth", token);//存到redis里边
                if (mtimeAuth==0){
                    return ResponseVo.systemException();
                }
                Jedis jedis = jedisAdapter.getJedis();
                ResponseVo responseVo = ResponseVo.ok();
                responseVo.setData(new MtimeAuthResponse(token, randomKey));
                return responseVo;
            } else {
                ResponseVo responseVo = ResponseVo.businessException();
                responseVo.setMsg("用户名或密码错误");
                return responseVo;
            }
        } catch (Exception e) {
            return ResponseVo.systemException();
        }

    }

    @Autowired
    private JwtProperties jwtProperties;

    @RequestMapping("/user/getUserInfo")
    public ResponseVo getUserInfo(HttpServletRequest request) {
        try {
            final String requestHeader = request.getHeader(jwtProperties.getHeader());
            String authToken = null;
            MtimeUserT mtimeUserT = null;
            if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
                authToken = requestHeader.substring(7);
                String usernameFromToken = jwtTokenUtil.getUsernameFromToken(authToken);
                List<MtimeUserT> users = mtimeUserService.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", usernameFromToken));
                mtimeUserT = users.get(0);
                ResponseUserModel responseUserModel = ResponseUserModel.toResponseUser(mtimeUserT);
                ResponseVo responseVo = ResponseVo.ok();
                responseVo.setData(responseUserModel);
                return responseVo;
            } else if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
                ResponseVo responseVo = ResponseVo.businessException();
                responseVo.setMsg("查询失败，用户尚未登陆");
                return responseVo;
            }
            return ResponseVo.systemException();
        } catch (Exception e) {
            return ResponseVo.systemException();
        }
    }

    @RequestMapping("/user/updateUserInfo")
    public ResponseVo updateUserInfo(ResponseUserModel requestUser) {
        try {
            MtimeUserT mtimeUserT = ResponseUserModel.toMtimeUser(requestUser);
            Date date = new Date();
            mtimeUserT.setUpdateTime(date);
            int update = mtimeUserService.update(mtimeUserT);
            if (update == 0) {
                ResponseVo responseVo = ResponseVo.businessException();
                responseVo.setMsg("用户信息修改失败");
                return responseVo;
            } else if (update == 1) {
                List<MtimeUserT> users = mtimeUserService.selectList(new EntityWrapper<MtimeUserT>().eq("uuid", mtimeUserT.getUuid()));
                MtimeUserT selectOne=null;
                if (users!=null) {
                    selectOne =users.get(0);
                }
                ResponseVo responseVo = ResponseVo.ok();
                ResponseUserModel responseUserModel = ResponseUserModel.toResponseUser(selectOne);
                responseVo.setData(responseUserModel);
                return responseVo;
            }
        } catch (Exception e) {
            return ResponseVo.systemException();
        }
        return ResponseVo.systemException();
    }

    @RequestMapping("/user/logout")
    public ResponseVo logout(HttpServletRequest request) {
        try {
            final String requestHeader = request.getHeader(jwtProperties.getHeader());
            String authToken = requestHeader.substring(7);
            String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(authToken);
            Long hdel = jedisAdapter.hdel(md5KeyFromToken, "MtimeAuth");
            if (hdel == 1) {
                ResponseVo responseVo = ResponseVo.ok();
                responseVo.setMsg("成功退出");
                return responseVo;
            } else if (hdel == 0) {
                ResponseVo responseVo = ResponseVo.businessException();
                responseVo.setMsg("退出失败，用户尚未登陆");
            }
        } catch (Exception e) {
            return ResponseVo.systemException();
        }
        return ResponseVo.systemException();

    }

    @RequestMapping("/user/check")
    public ResponseVo check(MtimeUserT mtimeUserT) {
        try {
            MtimeUserT selectOne = mtimeUserService.selectOne(mtimeUserT);
            if (selectOne == null) {
                ResponseVo responseVo = ResponseVo.ok();
                responseVo.setMsg("验证成功");
                return responseVo;
            } else {
                ResponseVo responseVo = ResponseVo.businessException();
                responseVo.setMsg("用户已存在");
                return responseVo;
            }
        } catch (Exception e) {
            return ResponseVo.systemException();
        }
    }
}
