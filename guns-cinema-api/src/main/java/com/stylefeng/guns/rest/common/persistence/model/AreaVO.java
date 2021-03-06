package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AreaVO implements Serializable {
    private int areaId;
    private String areaName;
    private boolean isActive;
}
