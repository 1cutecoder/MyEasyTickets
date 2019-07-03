package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.cskaoyan.model.*;
import com.cskaoyan.model.ImgVO;
import com.cskaoyan.service.FilmServiceApi;
import com.stylefeng.guns.rest.modular.film.vo.*;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@RestController
public class FilmController {

    @Reference(interfaceClass = FilmServiceApi.class)
    FilmServiceApi filmServiceApi;

    @RequestMapping("/film/getIndex")
    public ResponseVO getIndex() {
        FilmIndexVO filmIndexVO = new FilmIndexVO();
        List<BannerVO> banners = filmServiceApi.selectBanners();
        FilmVO hotFilm = filmServiceApi.getHotFilm(true, 8,1,2,99,99,99);
        FilmVO soonFilm = filmServiceApi.getSoonFilm(true, 8,1,2,99,99,99);
        List<FilmInfo> boxRanking = filmServiceApi.getBoxRanking();
        List<FilmInfo> expectRanking = filmServiceApi.getExpectRanking();
        List<FilmInfo> top = filmServiceApi.getTop();
        filmIndexVO.setBoxRanking(boxRanking);
        filmIndexVO.setExpectRanking(expectRanking);
        filmIndexVO.setTop100(top);
        filmIndexVO.setSoonFilms(soonFilm);
        filmIndexVO.setHotFilms(hotFilm);
        filmIndexVO.setBanners(banners);
        return ResponseVO.success(filmIndexVO);
    }
    @RequestMapping("/film/getConditionList")
    public ResponseVO getConditions(@RequestParam(name="catId",required = false,defaultValue = "99")String catId,
                                    @RequestParam(name="sourceId",required = false,defaultValue = "99")String sourceId,
                                    @RequestParam(name="yearId",required = false,defaultValue = "99")String yearId){
        boolean flag = false;//设置一个标志位
        //类型的集合
        List<CategoryVO> category = filmServiceApi.getCatgory();
        List<CategoryVO> catResult = new ArrayList<>();
        CategoryVO cat = null;
        //遍历取出来的类型集合
        for (CategoryVO categoryVO:category) {
            if (categoryVO.getCatId().equals("99")) {//判断是否为99，如果是就将全部的集合给到cat
                cat = categoryVO;
                continue;
            }
            if (categoryVO.getCatId().equals(catId)) {//如果取出来的id和传入的id一样，就将这个id对应的bean的active设为true
                flag = true;
                categoryVO.setIsActive(true);
            } else {//如果没有对应的，就设为false
                categoryVO.setIsActive(false);
            }
            catResult.add(categoryVO);//将vo重新传入集合
        }
            if(!flag){
              cat.setIsActive(true);
            }else {
                cat.setIsActive(false);
            }
            catResult.add(cat);

        //区域的集合
        flag = false;
        List<SourceVO> sources = filmServiceApi.getSource();
        List<SourceVO> sourceResult = new ArrayList<>();
        SourceVO sourceVO = null;
        for (SourceVO source : sources) {
            if (source.getSourceId().equals("99")) {
                sourceVO = source;
                continue;
            }
            if (source.getSourceId().equals(sourceId)) {
                flag = true;
                source.setIsActive(true);
            } else {
                source.setIsActive(false);
            }
            sourceResult.add(source);
        }
        // 如果不存在，则默认将全部变为Active状态
        if (!flag) {
            sourceVO.setIsActive(true);
            sourceResult.add(sourceVO);
        } else {
            sourceVO.setIsActive(false);
            sourceResult.add(sourceVO);
        }

        // 年代集合
        flag = false;
        List<YearVO> year = filmServiceApi.getYear();
        List<YearVO> yearResult = new ArrayList<>();
        YearVO yea = null;
        for (YearVO yearVo:year) {
            if (yearVo.getYearId().equals("99")) {
                yea = yearVo;
                continue;
            }
            if (yearVo.getYearId().equals(yearId)) {
                flag = true;
                yearVo.setIsActive(true);
            } else {
                yearVo.setIsActive(false);
            }
            yearResult.add(yearVo);
        }
            if (!flag){
                yea.setIsActive(true);
            }else {
                yea.setIsActive(false);
            }
            yearResult.add(yea);
        FilmConditionVO filmConditionVO = new FilmConditionVO();
        filmConditionVO.setCatInfo(catResult);
        filmConditionVO.setSourceInfo(sourceResult);
        filmConditionVO.setYearInfo(yearResult);
        return ResponseVO.success(filmConditionVO);
    }
    @RequestMapping("/film/getFilms")
    public ResponseVO getFilms(FilmRequestVO filmRequestVO){
        FilmVO filmVO = new FilmVO();
        // 根据showType来查询
        if(filmRequestVO.getShowType() == 1){
            filmVO = filmServiceApi.getHotFilm(false, filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),filmRequestVO.getSortId(),
                    filmRequestVO.getSourceId(),  filmRequestVO.getYearId(), filmRequestVO.getCatId());

        }
        if(filmRequestVO.getShowType() == 2){
            filmVO = filmServiceApi.getSoonFilm(false, filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),filmRequestVO.getSortId(),
                    filmRequestVO.getSourceId(),  filmRequestVO.getYearId(), filmRequestVO.getCatId());


        }
        if(filmRequestVO.getShowType() == 3){
            filmVO = filmServiceApi.getClassicFilms( filmRequestVO.getPageSize(), filmRequestVO.getNowPage(), filmRequestVO.getSortId(),
                    filmRequestVO.getSourceId(), filmRequestVO.getYearId(), filmRequestVO.getCatId());

        }
        String imgPre = "http://img.meetingshop.cn/";
        return ResponseVO.success(filmVO.getNowPage(),filmVO.getTotalPage(),imgPre,filmVO.getFilmInfo());
    }
    @RequestMapping("/film/films/{searchParam}")
    public ResponseVO films(int searchType,@PathVariable("searchParam") String searchParam) throws ExecutionException, InterruptedException {
        FilmDetailVO filmDetails = filmServiceApi.getFilmDetails(searchType, searchParam);
        if (filmDetails == null) {
            return ResponseVO.serviceFail("没有可查询的影片");
        } else if (filmDetails.getFilmId() == null || filmDetails.getFilmId().trim().length() == 0) {
            return ResponseVO.serviceFail("没有可查询的影片");
        }

        String filmId = filmDetails.getFilmId();
        //得到filmId之后，将info04的信息查出来；
        //biography
        FilmDescVO filmDesc = filmServiceApi.getFilmDesc(filmId);

        // 获取图片信息
        ImgVO imgs = filmServiceApi.getImgs(filmId);

        // 获取导演信息
        ActorVO actorVO = filmServiceApi.getDectInfo(filmId);

        // 获取演员信息
        List<ActorVO> actors = filmServiceApi.getActors(filmId);


        // 组织info对象
        InfoRequestVO infoRequestVO = new InfoRequestVO();

        // 组织Actor属性
        ActorRequestVO actorRequestVO = new ActorRequestVO();
        actorRequestVO.setActors(actors);
        actorRequestVO.setDirector(actorVO);

        // 组织info对象
        infoRequestVO.setActors(actorRequestVO);
        infoRequestVO.setBiography(filmDesc.getBiography());
        infoRequestVO.setFilmId(filmId);
        infoRequestVO.setImgVO(imgs);

        // 组织成返回值
        filmDetails.setInfo04(infoRequestVO);

        return ResponseVO.success("http://img.meetingshop.cn/", filmDetails);
    }


}

