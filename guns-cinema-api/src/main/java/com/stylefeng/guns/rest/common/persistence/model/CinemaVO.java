package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaVO implements Serializable {
    private int uuid;
    private String cinemaName;
    private String address;
    private String minimumPrice;
}
