package com.stylefeng.guns.rest.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.rest.common.persistence.model.CinemaInfoVO;
import com.stylefeng.guns.rest.common.persistence.model.CinemaQueryVO;
import com.stylefeng.guns.rest.common.persistence.model.CinemaVO;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCinemaT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 * 影院信息表 Mapper 接口
 * </p>
 *
 * @author Janloopeak
 * @since 2019-06-06
 */
@Mapper
public interface MtimeCinemaTMapper extends BaseMapper<MtimeCinemaT> {
    Integer getCount(CinemaQueryVO cinemaQueryVO) throws DataAccessException;

    List<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO)throws DataAccessException;

    CinemaInfoVO getCinemaInfo(int cinemaId)throws DataAccessException;

    MtimeCinemaT getCinema(int cinemaId);
}
