package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.model.*;
import com.cskaoyan.service.FilmServiceApi;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Service(interfaceClass = FilmServiceApi.class)
public class FilmServiceApiImpl implements FilmServiceApi {
    @Autowired
    private MtimeBannerTMapper mtimeBannerTMapper;
    @Autowired
     private MtimeFilmTMapper mtimeFilmTMapper;
    @Autowired
    private MtimeCatDictTMapper mtimeCatDictTMapper;
    @Autowired
    private MtimeSourceDictTMapper mtimeSourceDictTMapper;
    @Autowired
    private MtimeYearDictTMapper mtimeYearDictTMapper;
    @Autowired
    private MtimeFilmInfoTMapper mtimeFilmInfoTMapper;
    @Autowired
    private MtimeActorTMapper mtimeActorTMapper;
    @Override
    public List<BannerVO> selectBanners() {
        ArrayList<BannerVO> list = new ArrayList<>();
        List<MtimeBannerT> bannerVOS = mtimeBannerTMapper.selectBanners();
        for (MtimeBannerT mtimeBannerT : bannerVOS) {
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerId(mtimeBannerT.getUuid() + "");
            bannerVO.setBannerAddress(mtimeBannerT.getBannerAddress());
            bannerVO.setBannerUrl(mtimeBannerT.getBannerUrl());
            list.add(bannerVO);
        }
        return list;
    }
    @Override
    public FilmVO getHotFilm(boolean isLimit, int nums,int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        if(isLimit){
            Page<MtimeFilmT> page = new Page<>(1,nums);
            List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page, entityWrapper);
            filmInfos = getFilmInfos(mtimeFilmTS);
            filmVO.setFilmInfo(filmInfos);
            filmVO.setFilmNum(filmInfos.size());
        }else {
           // 不是首页，但是要根据sortId去查询
            Page<MtimeFilmT> page = null;
            if(sortId == 1){
                page = new Page<>(nowPage,nums,"film_box_office");
            }
            if (sortId == 2){
                page = new Page<>(nowPage,nums,"film_time");
            }
            if(sortId == 3){
                page = new Page<>(nowPage,nums,"film_score");
            }
            if (sourceId != 99){
                entityWrapper.eq("film_source",sortId);
            }
            if(yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }
            if (catId != 99){
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }
            List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page, entityWrapper);
             filmInfos = getFilmInfos(mtimeFilmTS);
             filmVO.setFilmNum(filmInfos.size());
             //分页+总页数
            int count = mtimeFilmTMapper.selectCount(entityWrapper);
            int totalPage = (count/nums) + 1;
            filmVO.setFilmInfo(filmInfos);
            filmVO.setTotalPage(totalPage);
            filmVO.setNowPage(nowPage);
        }
        return filmVO;
    }

    @Override
    public FilmVO getSoonFilm(boolean isLimit, int nums,int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);
        if(isLimit){
            Page<MtimeFilmT> page = new Page<>(1,nums);
            List<MtimeFilmT> soonfilms = mtimeFilmTMapper.selectPage(page, entityWrapper);
            filmInfos = getFilmInfos(soonfilms);
            filmVO.setFilmNum(filmInfos.size());
            filmVO.setFilmInfo(filmInfos);
        }else{
            Page<MtimeFilmT> page = null;
            if(sortId == 1){
                page = new Page<>(nowPage,nums,"film_preSaleNum");
            }
            if (sortId == 2){
                page = new Page<>(nowPage,nums,"film_time");
            }
            if (sourceId != 99){
                entityWrapper.eq("film_source",sortId);
            }
            if(yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }
            if (catId != 99){
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }
            List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page, entityWrapper);
            filmInfos = getFilmInfos(mtimeFilmTS);
            filmVO.setFilmNum(filmInfos.size());
            //分页+总页数
            int count = mtimeFilmTMapper.selectCount(entityWrapper);
            int totalPage = (count/nums) + 1;
            filmVO.setFilmInfo(filmInfos);
            filmVO.setTotalPage(totalPage);
            filmVO.setNowPage(nowPage);
        }
        return filmVO;
    }

    @Override
    public FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        Page<MtimeFilmT> page = null;
        if(sortId == 1){
            page = new Page<>(nowPage,nums,"film_box_office");
        }
        if (sortId == 2){
            page = new Page<>(nowPage,nums,"film_time");
        }
        if(sortId == 3){
            page = new Page<>(nowPage,nums,"film_score");
        }
        if (sourceId != 99){
            entityWrapper.eq("film_source",sortId);
        }
        if(yearId != 99){
            entityWrapper.eq("film_date",yearId);
        }
        if (catId != 99){
            String catStr = "%#" + catId + "#%";
            entityWrapper.like("film_cats", catStr);
        }
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page, entityWrapper);
        filmInfos = getFilmInfos(mtimeFilmTS);
        filmVO.setFilmNum(filmInfos.size());
        //分页+总页数
        int count = mtimeFilmTMapper.selectCount(entityWrapper);
        int totalPage = (count/nums) + 1;
        filmVO.setFilmInfo(filmInfos);
        filmVO.setTotalPage(totalPage);
        filmVO.setNowPage(nowPage);
        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        Page<MtimeFilmT> page = new Page<>(1,10,"film_box_office");
        List<MtimeFilmT> boxRanking = mtimeFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> filmInfos = getFilmInfos(boxRanking);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);
        Page<MtimeFilmT> page = new Page<>(1,10,"film_preSaleNum");
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> expectRanking = getFilmInfos(mtimeFilmTS);
        return expectRanking;
    }

    @Override
    public List<FilmInfo> getTop() {
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        Page<MtimeFilmT> page = new Page<>(1,10,"film_score");
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> getTop = getFilmInfos(mtimeFilmTS);
        return getTop;
    }

    private List<FilmInfo> getFilmInfos(List<MtimeFilmT> moocFilms) {
        List<FilmInfo> filmInfos = new ArrayList<>();
        for (MtimeFilmT moocFilmT : moocFilms) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setFilmId(moocFilmT.getUuid() + "");
            filmInfo.setExpectNum(moocFilmT.getFilmPresalenum());
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));

            // 将转换的对象放入结果集
            filmInfos.add(filmInfo);
        }
        return filmInfos;
        }

    @Override
    public List<CategoryVO> getCatgory() {
        List<CategoryVO> category = new ArrayList<>();
        List<MtimeCatDictT> mtimeCatDictTS = mtimeCatDictTMapper.selectList(null);
        for (MtimeCatDictT cates:mtimeCatDictTS) {
            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setCatId(cates.getUuid()+ "");
            categoryVO.setCatName(cates.getShowName());
            category.add(categoryVO);
        }
        return category;
    }

    @Override
    public List<SourceVO> getSource() {
        List<SourceVO> source = new ArrayList<>();
        List<MtimeSourceDictT> mtimeSourceDictTS = mtimeSourceDictTMapper.selectList(null);
        for (MtimeSourceDictT sourceDict: mtimeSourceDictTS) {
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(sourceDict.getUuid()+"");
            sourceVO.setSourceName(sourceDict.getShowName());
            source.add(sourceVO);
        }
        return source;
    }

    @Override
    public List<YearVO> getYear() {
        List<YearVO> year = new ArrayList<>();
        List<MtimeYearDictT> mtimeYearDictTS = mtimeYearDictTMapper.selectList(null);
        for (MtimeYearDictT yearDict:mtimeYearDictTS) {
            YearVO yearVO = new YearVO();
            yearVO.setYearId(yearDict.getUuid()+"");
            yearVO.setYearName(yearDict.getShowName());
            year.add(yearVO);
        }
        return year;
    }

    @Override
    public FilmDetailVO getFilmDetails(int searchType, String searchParam) {
        if(searchType == 1){
            FilmDetailVO detailsVo = mtimeFilmTMapper.selectFilmDetailsByName("%" + searchParam + "%");
            return detailsVo;
        }else{
            FilmDetailVO filmDetailVO = mtimeFilmTMapper.selectFilmDetailsById(searchParam);
            return filmDetailVO;
        }
    }
    private MtimeFilmInfoT getFilmInfo(String filmId){
        MtimeFilmInfoT mtimeFilmInfoT = new MtimeFilmInfoT();
        mtimeFilmInfoT.setFilmId(filmId);
        MtimeFilmInfoT filmInfoT = mtimeFilmInfoTMapper.selectOne(mtimeFilmInfoT);
        return filmInfoT;

    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MtimeFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(moocFilmInfoT.getBiography());
        filmDescVO.setFilmId(filmId);

        return filmDescVO;

    }


    @Override
    public ImgVO getImgs(String filmId) {

        MtimeFilmInfoT moocFilmInfoT = getFilmInfo(filmId);
        // 图片地址是五个以逗号为分隔的链接URL
        String filmImgStr = moocFilmInfoT.getFilmImgs();
        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO();
        imgVO.setMainImg(filmImgs[0]);
        imgVO.setImg01(filmImgs[1]);
        imgVO.setImg02(filmImgs[2]);
        imgVO.setImg03(filmImgs[3]);
        imgVO.setImg04(filmImgs[4]);

        return imgVO;
    }

    @Override
    public ActorVO getDectInfo(String filmId) {

        MtimeFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        // 获取导演编号
        Integer directId = moocFilmInfoT.getDirectorId();

        MtimeActorT moocActorT = mtimeActorTMapper.selectById(directId);

        ActorVO actorVO = new ActorVO();
        actorVO.setImgAddress(moocActorT.getActorImg());
        actorVO.setDirectorName(moocActorT.getActorName());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {

        List<ActorVO> actors = mtimeActorTMapper.getActors(filmId);
        return actors;
    }
}
