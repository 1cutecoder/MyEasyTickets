package com.stylefeng.guns.rest.modular.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVO<T> implements Serializable {
    // 返回状态码
    private int status;
    // 返回信息
    private String msg;
    // 返回数据实体
    private T data;
    //图片
    private String imgPre;
    //分页
    private int nowPage;
    private int totalPage;
    private ResponseVO() {

    }

    public static<T> ResponseVO success(int nowPage,int totalPage,String imgPre,T t){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(t);
        responseVO.setImgPre(imgPre);
        responseVO.setTotalPage(totalPage);
        responseVO.setNowPage(nowPage);

        return responseVO;
    }

    public static<T> ResponseVO success(String imgPre,T t){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(t);
        responseVO.setImgPre(imgPre);

        return responseVO;
    }

    public static<T> ResponseVO success(T t){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(t);

        return responseVO;
    }

    public static<T> ResponseVO success(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<T> ResponseVO serviceFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<T> ResponseVO appFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);

        return responseVO;
    }



}
