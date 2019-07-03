package com.cskaoyan.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryVO implements Serializable {
    private String catId;
    private String catName;
    private Boolean isActive;
}
