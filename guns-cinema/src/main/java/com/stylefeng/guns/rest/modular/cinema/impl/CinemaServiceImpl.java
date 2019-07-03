package com.stylefeng.guns.rest.modular.cinema.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFieldTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeHallFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.modular.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(interfaceClass = CinemaService.class)
@Component
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    private MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;

    @Autowired
    private MtimeFieldTMapper mtimeFieldTMapper;


    @Override
    public Integer getCinemas_totalPage(CinemaQueryVO cinemaQueryVO) {
        Integer count = mtimeCinemaTMapper.getCount(cinemaQueryVO);
        if(count % cinemaQueryVO.getPageSize() == 0){
            return count / cinemaQueryVO.getPageSize();
        } else{
            return count / cinemaQueryVO.getPageSize() + 1;
        }
    }

    @Override
    public List<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {
        List<CinemaVO> page = mtimeCinemaTMapper.getCinemas(cinemaQueryVO);
        return page;
    }

    @Override
    public FilmInfoVO getFilmInfo(int fieldId) {
        FilmInfoVO filmInfoVO = mtimeHallFilmInfoTMapper.getFilmInfo(fieldId);
        return filmInfoVO;
    }

    @Override
    public CinemaInfoVO getCinemaInfo(int cinemaId) {
        CinemaInfoVO cinemaInfoVO = mtimeCinemaTMapper.getCinemaInfo(cinemaId);
        return cinemaInfoVO;
    }

    @Override
    public HallInfoVO getHallInfo(int fieldId) {
        HallInfoVO hallInfoVO = mtimeFieldTMapper.getHallInfo(fieldId);
        return hallInfoVO;
    }

    @Override
    public MtimeCinemaT getCinema(int cinemaId) {
        MtimeCinemaT mtimeCinemaT = mtimeCinemaTMapper.selectById(cinemaId);
        return mtimeCinemaT;
    }

/*  @Override
    public List<BrandVO> getBrands(int brandId) {
        return null;
    }

    @Override
    public List<AreaVO> getAreas(int areaId) {
        return null;
    }

    @Override
    public List<HallTypeVO> getHallTypes(int hallType) {
        return null;
    }

    @Override
    public CinemaInfoVO getCinemaInfoById(int cinemaId) {
        return null;
    }

    @Override
    public List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId) {
        return null;
    }

    @Override
    public HallInfoVO getFilmFieldInfo(int fieldId) {
        return null;
    }

    @Override
    public FilmInfoVO getFilmInfoByFieldId(int fieldId) {
        return null;
    }*/
}
