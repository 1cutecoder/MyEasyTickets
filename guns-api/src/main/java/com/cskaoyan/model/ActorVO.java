package com.cskaoyan.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActorVO implements Serializable {
    private String imgAddress;
    private String directorName;
    private String roleName;
}
