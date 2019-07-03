package com.stylefeng.guns.rest.modular.film.util;

/**
 * Created by cute coder
 * 2019/6/5 17:16
 */
public class ResponseVo<T> {

    private int status;

    private T data;

    private String msg;

    public static ResponseVo ok(){
        ResponseVo<Object> responseVo = new ResponseVo<>();
        responseVo.setStatus(0);
        return responseVo;
    }

    public static ResponseVo businessException(){
        ResponseVo<Object> responseVo = new ResponseVo<>();
        responseVo.setStatus(1);
        return responseVo;
    }

    public static ResponseVo systemException(){
        ResponseVo<Object> responseVo = new ResponseVo<>();
        responseVo.setStatus(999);
        responseVo.setMsg("系统出现异常，请联系管理员");
        return responseVo;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
