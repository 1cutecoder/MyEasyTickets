package com.stylefeng.guns.rest.modular.cinema.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeHallFilmInfoTMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.rest.common.persistence.model.MtimeHallFilmInfoT;
import com.stylefeng.guns.rest.modular.cinema.service.IMtimeHallFilmInfoTService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 影厅电影信息表 服务实现类
 * </p>
 *
 * @author cutecoder
 * @since 2019-06-13
 */
@Service(interfaceClass = IMtimeHallFilmInfoTService.class)
@Component
public class MtimeHallFilmInfoTServiceImpl extends ServiceImpl<MtimeHallFilmInfoTMapper, MtimeHallFilmInfoT> implements IMtimeHallFilmInfoTService {

}
