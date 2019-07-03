package com.cskaoyan.service;

import com.cskaoyan.model.*;

import java.util.List;

public interface FilmServiceApi {
    //获取banners
    List<BannerVO> selectBanners();
    // 获取热映影片
    FilmVO getHotFilm(boolean isLimit, int nums ,int nowPage, int sortId, int sourceId, int yearId, int catId);
    //获取即将上映影片
    FilmVO getSoonFilm(boolean isLimit, int nums,int nowPage, int sortId, int sourceId, int yearId, int catId );
    // 获取票房排行榜
    List<FilmInfo> getBoxRanking();
    //获取期待排行榜
    List<FilmInfo> getExpectRanking();
    //获取Top100
    List<FilmInfo> getTop();
    // 获取经典影片
    FilmVO getClassicFilms(int nums,int nowPage, int sortId, int sourceId, int yearId, int catId);
    //获取电影类型
    List<CategoryVO> getCatgory();
    //获取区域
    List<SourceVO> getSource();
    //获取年代
    List<YearVO> getYear();
    //连接查询film_info,filmt,演员表还有导演表
    FilmDetailVO getFilmDetails(int searchType, String searchParam);

    FilmDescVO getFilmDesc(String filmId);

    ImgVO getImgs(String filmId);

    // 获取导演信息
    ActorVO getDectInfo(String filmId);

    // 获取演员信息
    List<ActorVO> getActors(String filmId);
}
