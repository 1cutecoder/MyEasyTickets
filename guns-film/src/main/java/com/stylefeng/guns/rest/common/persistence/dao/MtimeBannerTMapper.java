package com.stylefeng.guns.rest.common.persistence.dao;

import com.cskaoyan.model.BannerVO;
import com.stylefeng.guns.rest.common.persistence.model.MtimeBannerT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * banner信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-04
 */
@Mapper
public interface MtimeBannerTMapper extends BaseMapper<MtimeBannerT> {

    List<MtimeBannerT> selectBanners();
}
