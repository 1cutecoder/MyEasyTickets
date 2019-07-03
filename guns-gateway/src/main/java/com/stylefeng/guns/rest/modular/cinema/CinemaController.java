package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.model.FilmInfo;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.modular.cinema.service.*;
import com.stylefeng.guns.rest.modular.film.util.ResponseVo;
import com.stylefeng.guns.rest.modular.film.util.StringToIntegerUtil;
import com.stylefeng.guns.rest.modular.film.vo.ResponseVO;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/cinema")
public class CinemaController {

    @Reference
    CinemaService cinemaService;

    @Reference
    IMtimeAreaDictTService areaDictTService;

    @Reference
    IMtimeBrandDictTService brandDictTService;

    @Reference
    IMtimeHallDictTService hallDictTService;

    @Reference
    IMtimeHallFilmInfoTService hallFilmInfoTService;

    @Reference
    IMtimeFieldTService fieldTService;

    @RequestMapping("/getCinemas")
    public HashMap<String, Object> getCinemas(CinemaQueryVO cinemaQueryVO) {
        HashMap<String, Object> map = new LinkedHashMap<>();
        HashMap<String, Object> map_cinemas = new LinkedHashMap<>();

        try {
            List<CinemaVO> cinemas = cinemaService.getCinemas(cinemaQueryVO);
            Integer totalPage = cinemaService.getCinemas_totalPage(cinemaQueryVO);
            if (cinemas == null || totalPage == null) {
                map.put("status", 1);
                map.put("msg", "影院信息查询失败");//业务异常
                return map;
            } else {
                map.put("status", 0);
                map.put("nowPage", cinemaQueryVO.getNowPage());
                map.put("totalPage", (int) totalPage);
                map.put("data", cinemas);
                return map;
            }

        } catch (DataAccessException e) {
            map.put("status", 999);
            map.put("msg", "系统出现异常，请联系管理员");//系统异常
            return map;
        }
    }


    @RequestMapping("/getCondition")
    public ResponseVo getCondition(Integer brandId, Integer hallType, Integer areaId) {
        try {
            HashMap<String, Object> data = new HashMap<>();
            List<BrandVO> brandList = new ArrayList<>();
            List<AreaVO> areaList = new ArrayList<>();
            List<HallTypeVO> halltypeList = new ArrayList<>();
            getBrands(brandId, brandList);
            data.put("brandList", brandList);
            getAreas(areaId, areaList);
            data.put("areaList", areaList);
            getHallTypes(hallType, halltypeList);
            data.put("halltypeList", halltypeList);
            ResponseVo responseVo = ResponseVo.ok();
            responseVo.setData(data);
            return responseVo;
        } catch (NullPointerException e) {
            ResponseVo responseVo = ResponseVo.businessException();
            responseVo.setMsg("影院信息查询失败");
            e.printStackTrace();
            return responseVo;
        } catch (Exception e) {
            return ResponseVo.systemException();
        }
    }

    private void getHallTypes(Integer hallType, List<HallTypeVO> halltypeList) {
        if (hallType == null || 99==hallType) {
            List<MtimeHallDictT> hallDictTList = hallDictTService.selectList(new EntityWrapper<MtimeHallDictT>().between("UUID", "1", "99"));
            toHallTypeVOs(hallDictTList, halltypeList);
        } else {
            List<MtimeHallDictT> hallDictTList = hallDictTService.selectList(new EntityWrapper<MtimeHallDictT>().eq("UUID", hallType));
            toHallTypeVOs(hallDictTList, halltypeList);
        }
    }

    private void toHallTypeVOs(List<MtimeHallDictT> hallDictTList, List<HallTypeVO> halltypeList) {
        if (hallDictTList != null) {
            for (int i = 0; i < hallDictTList.size(); i++) {
                HallTypeVO hallTypeVO = new HallTypeVO();
                hallTypeVO.setActive(true);
                hallTypeVO.setHalltypeId(hallDictTList.get(i).getUuid());
                hallTypeVO.setHalltypeName(hallDictTList.get(i).getShowName());
                halltypeList.add(hallTypeVO);
            }
        }
    }

    private void getAreas(Integer areaId, List<AreaVO> areaList) {
        if (areaId == null || 99==areaId) {
            List<MtimeAreaDictT> areaDictTList = areaDictTService.selectList(new EntityWrapper<MtimeAreaDictT>().between("UUID", "1", "99"));
            toAreaVOs(areaDictTList, areaList);
        } else {
            MtimeAreaDictT mtimeAreaDictT = areaDictTService.selectById(areaId);
            if (mtimeAreaDictT == null) {
                return;
            }
            AreaVO areaVO = new AreaVO();
            areaVO.setActive(true);
            areaVO.setAreaId(mtimeAreaDictT.getUuid());
            areaVO.setAreaName(mtimeAreaDictT.getShowName());
            areaList.add(areaVO);
        }
    }

    private void toAreaVOs(List<MtimeAreaDictT> areaDictTList, List<AreaVO> areaList) {
        if (areaDictTList != null) {
            for (int i = 0; i < areaDictTList.size(); i++) {
                AreaVO areaVO = new AreaVO();
                areaVO.setActive(true);
                areaVO.setAreaId(areaDictTList.get(i).getUuid());
                areaVO.setAreaName(areaDictTList.get(i).getShowName());
                areaList.add(areaVO);
            }
        }

    }


    private void getBrands(Integer brandId, List<BrandVO> brandList) {
        if (brandId == null || 99==brandId) {
            List<MtimeBrandDictT> mtimeBrandDictTS = brandDictTService.selectList(new EntityWrapper<MtimeBrandDictT>().between("UUID", "1", "99"));
            toBrandVOs(mtimeBrandDictTS, brandList);
        } else {
            MtimeBrandDictT mtimeBrandDictT = brandDictTService.selectById(brandId);
            if (mtimeBrandDictT == null) {
                return;
            }
            BrandVO brandVO = new BrandVO();
            brandVO.setActive(true);
            brandVO.setBrandId(mtimeBrandDictT.getUuid());
            brandVO.setBrandName(mtimeBrandDictT.getShowName());
            brandList.add(brandVO);
        }
    }

    private void toBrandVOs(List<MtimeBrandDictT> mtimeBrandDictTS, List<BrandVO> brands) {
        for (int i = 0; i < mtimeBrandDictTS.size(); i++) {
            BrandVO brandVO = new BrandVO();
            brandVO.setActive(true);
            brandVO.setBrandId(mtimeBrandDictTS.get(i).getUuid());
            brandVO.setBrandName(mtimeBrandDictTS.get(i).getShowName());
            brands.add(brandVO);
        }

    }

    @RequestMapping("/getFieldInfo")
    public HashMap<String, Object> getFieldInfo(Integer cinemaId, Integer fieldId) {
        HashMap<String, Object> map = new LinkedHashMap<>();
        HashMap<String, Object> map_fieldInfo = new LinkedHashMap<>();
        HashMap<String, Object> map_filmInfo = new LinkedHashMap<>();

        try {
            FilmInfoVO filmInfo = cinemaService.getFilmInfo(fieldId);
            CinemaInfoVO cinemaInfo = cinemaService.getCinemaInfo(cinemaId);
            HallInfoVO hallInfo = cinemaService.getHallInfo(fieldId);
            if (filmInfo == null || cinemaInfo == null || hallInfo == null) {
                map.put("status", 1);
                map.put("msg", "场次信息查询失败");//业务异常
                return map;
            } else {
                map_filmInfo.put("filmId", filmInfo.getFilmId());
                map_filmInfo.put("filmName", filmInfo.getFilmName());
                map_filmInfo.put("filmType", filmInfo.getFilmType());
                map_filmInfo.put("imgAddress", filmInfo.getImgAddress());
                map_filmInfo.put("filmCats", filmInfo.getFilmCats());
                map_filmInfo.put("filmLength", filmInfo.getFilmLength());

                map_fieldInfo.put("filmInfo", map_filmInfo);
                map_fieldInfo.put("cinemaInfo", cinemaInfo);
                map_fieldInfo.put("hallInfo", hallInfo);

                map.put("status", 0);
                map.put("imgPre", "http://img.meetingshop.cn/");
                map.put("data", map_fieldInfo);
                return map;
            }

        } catch (DataAccessException e) {
            map.put("status", 999);
            map.put("msg", "系统出现异常，请联系管理员");//系统异常
            return map;
        }
    }

    @RequestMapping("/getFields")
    public ResponseVO getFields(Integer cinemaId) {
        try {
            String imgPre = "https://cskanyanmall-file.oss-cn-hangzhou.aliyuncs.com/";
            HashMap<String, Object> data = new HashMap<>();
            CinemaInfoVO cinemaInfo = cinemaService.getCinemaInfo(cinemaId);
            data.put("cinemaInfo", cinemaInfo);
            MtimeCinemaT cinema = cinemaService.getCinema(cinemaId);
            String hallIdStr = cinema.getHallIds();
            Integer[] hallIds = StringToIntegerUtil.getArray(hallIdStr);
            List<Object> hallIdList = new ArrayList<>();
            for (int i = 0; i < hallIds.length; i++) {
                hallIdList.add(hallIds[i]);

            }
            List<MtimeHallFilmInfoT> hallFilmInfoTS = hallFilmInfoTService.selectList(new EntityWrapper<MtimeHallFilmInfoT>().in("UUID", hallIdList));
            ArrayList<FilmInfoVO> filmList = new ArrayList<>();
            getFilmInfoVOs(filmList, hallFilmInfoTS, cinemaId);
            data.put("filmList", filmList);
            return ResponseVO.success(imgPre, data);
        } catch (NullPointerException e) {
            return ResponseVO.serviceFail("影院信息查询失败");
        } catch (Exception e) {
            return ResponseVO.appFail("系统出现异常，请联系管理员");
        }
    }

    private void getFilmInfoVOs(List<FilmInfoVO> filmList, List<MtimeHallFilmInfoT> hallFilmInfoTS, Integer cinemaId) {
        for (int i = 0; i < hallFilmInfoTS.size(); i++) {
            FilmInfoVO filmInfoVO = new FilmInfoVO();
            filmInfoVO.setImgAddress(hallFilmInfoTS.get(i).getImgAddress());
            filmInfoVO.setActors(hallFilmInfoTS.get(i).getActors());
            filmInfoVO.setFilmCats(hallFilmInfoTS.get(i).getFilmCats());
            filmInfoVO.setFilmId(hallFilmInfoTS.get(i).getFilmId());
            filmInfoVO.setFilmLength(hallFilmInfoTS.get(i).getFilmLength());
            filmInfoVO.setFilmName(hallFilmInfoTS.get(i).getFilmName());
            filmInfoVO.setFilmType(hallFilmInfoTS.get(i).getFilmLanguage());
            HashMap<String, Object> columnMap = new HashMap<>();
            columnMap.put("cinema_id", cinemaId);
            columnMap.put("film_id", hallFilmInfoTS.get(i).getFilmId());
            List<MtimeFieldT> mtimeFieldTS = fieldTService.selectByMap(columnMap);
            ArrayList<FilmFieldVO> filmFieldVOs = new ArrayList<>();
            toFilmFieldVO(mtimeFieldTS, filmFieldVOs);
            filmInfoVO.setFilmFields(filmFieldVOs);
            filmList.add(filmInfoVO);
        }
    }

    private void toFilmFieldVO(List<MtimeFieldT> mtimeFieldTS, ArrayList<FilmFieldVO> filmFieldVOs) {
        for (int i = 0; i < mtimeFieldTS.size(); i++) {
            FilmFieldVO filmFieldVO = new FilmFieldVO();
            filmFieldVO.setFieldId(mtimeFieldTS.get(i).getUuid());
            filmFieldVO.setBeginTime(mtimeFieldTS.get(i).getBeginTime());
            filmFieldVO.setEndTime(mtimeFieldTS.get(i).getEndTime());
            filmFieldVO.setLanguage("中文2D");
            filmFieldVO.setHallName(mtimeFieldTS.get(i).getHallName());
            filmFieldVO.setPrice(mtimeFieldTS.get(i).getPrice());
            filmFieldVOs.add(filmFieldVO);
        }
    }

}
