package com.stylefeng.guns.rest.modular.auth.controller.dto;



/**
 * 认证的响应结果
 *
 * @author fengshuonan
 * @Date 2017/8/24 13:58
 */
public class MtimeAuthResponse {


    /**
     * jwt token
     */
    private final String token;

    /**
     * 用于客户端混淆md5加密
     */
    private final String randomKey;

    public MtimeAuthResponse(String token, String randomKey) {
        this.token = token;
        this.randomKey = randomKey;
    }

    public String getToken() {
        return this.token;
    }

    public String getRandomKey() {
        return randomKey;
    }
}
