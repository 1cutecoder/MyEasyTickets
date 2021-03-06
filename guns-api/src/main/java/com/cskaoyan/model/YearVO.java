package com.cskaoyan.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class YearVO implements Serializable {
    private String yearId;
    private String yearName;
    private Boolean isActive;
}
