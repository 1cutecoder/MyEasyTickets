package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.FilmInfoVO;
import com.stylefeng.guns.rest.common.persistence.model.MtimeHallFilmInfoT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

/**
 * <p>
 * 影厅电影信息表 Mapper 接口
 * </p>
 *
 * @author Janloopeak
 * @since 2019-06-06
 */
public interface MtimeHallFilmInfoTMapper extends BaseMapper<MtimeHallFilmInfoT> {
    FilmInfoVO getFilmInfo(@Param("fieldId") int fieldId) throws DataAccessException;
}
