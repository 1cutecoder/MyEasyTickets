package com.stylefeng.guns.rest.common.persistence.dao;

import com.cskaoyan.model.FilmDetailVO;
import com.cskaoyan.model.FilmVO;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-04
 */
public interface MtimeFilmTMapper extends BaseMapper<MtimeFilmT> {

    FilmVO selectFilmInfo();

    FilmDetailVO selectFilmDetailsByName( @Param("filmName") String filmName);

    FilmDetailVO selectFilmDetailsById(@Param("uuid") String searchParam);
}
