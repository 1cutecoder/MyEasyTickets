package com.cskaoyan.model;

import lombok.Data;

import java.util.List;

@Data
public class ActorRequestVO {
    private ActorVO director;
    private List<ActorVO> actors;
}
