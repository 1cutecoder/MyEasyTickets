package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.HallInfoVO;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.dao.DataAccessException;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author Janloopeak
 * @since 2019-06-06
 */
public interface MtimeFieldTMapper extends BaseMapper<MtimeFieldT> {
    HallInfoVO getHallInfo(int fieldId)throws DataAccessException;
}
