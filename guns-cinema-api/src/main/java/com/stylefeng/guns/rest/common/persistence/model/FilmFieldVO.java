package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmFieldVO implements Serializable {
    private int fieldId;
    private String beginTime;
    private String endTime;
    private String language;
    private String hallName;
    private Integer price;
}
