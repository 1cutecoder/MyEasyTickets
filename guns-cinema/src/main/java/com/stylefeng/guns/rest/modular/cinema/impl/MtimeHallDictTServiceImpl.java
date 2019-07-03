package com.stylefeng.guns.rest.modular.cinema.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeHallDictTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeHallDictT;
import com.stylefeng.guns.rest.modular.cinema.service.IMtimeHallDictTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 地域信息表 服务实现类
 * </p>
 *
 * @author cutecoder
 * @since 2019-06-13
 */
@Service(interfaceClass = IMtimeHallDictTService.class)
@Component
public class MtimeHallDictTServiceImpl extends ServiceImpl<MtimeHallDictTMapper, MtimeHallDictT> implements IMtimeHallDictTService {

}
