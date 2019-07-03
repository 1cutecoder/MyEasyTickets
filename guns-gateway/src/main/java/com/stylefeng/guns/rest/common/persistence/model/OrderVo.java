package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by cute coder
 * 2019/6/14 23:11
 */
@Data
public class OrderVo {
    private String orderId;
    private String filmName;
    private String fieldTime;
    private String cinemaName;
    private String seatsName;
    private double orderPrice;
    private Date orderTimestamp;
}
