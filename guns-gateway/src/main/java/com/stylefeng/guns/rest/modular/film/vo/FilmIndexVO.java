package com.stylefeng.guns.rest.modular.film.vo;

import com.cskaoyan.model.BannerVO;
import com.cskaoyan.model.FilmInfo;
import com.cskaoyan.model.FilmVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmIndexVO implements Serializable {
    private List<BannerVO> banners;
    private FilmVO hotFilms;
    private FilmVO soonFilms;
    private List<FilmInfo> boxRanking;
    private List<FilmInfo> expectRanking;
    private List<FilmInfo> top100;
}
