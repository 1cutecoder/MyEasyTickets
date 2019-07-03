package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallTypeVO implements Serializable {
    private int halltypeId;
    private String halltypeName;
    private boolean isActive;
}
