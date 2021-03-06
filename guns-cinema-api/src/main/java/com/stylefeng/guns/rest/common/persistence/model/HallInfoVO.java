package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallInfoVO implements Serializable {
    private int hallFieldId;
    private String hallName;
    private String price;
    private String seatFile;
    private String soldSeats;
}
