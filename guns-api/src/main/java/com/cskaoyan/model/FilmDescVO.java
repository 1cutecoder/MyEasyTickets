package com.cskaoyan.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmDescVO implements Serializable {
    private String biography;
    private String filmId;
}