package com.stylefeng.guns.rest.modular.cinema.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.common.persistence.model.*;

import java.util.List;

public interface CinemaService {
     Integer getCinemas_totalPage(CinemaQueryVO cinemaQueryVO);

     List<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO);

     /*List<BrandVO> getBrands(int brandId);

     List<AreaVO> getAreas(int areaId);

     List<HallTypeVO> getHallTypes(int hallType);*/

     CinemaInfoVO getCinemaInfo(int cinemaId);

     FilmInfoVO getFilmInfo(int fieldId);

     HallInfoVO getHallInfo(int fieldId);

     MtimeCinemaT getCinema(int cinemaId);

     //FilmInfoVO getFilmInfoByFieldId(int fieldId);
}
