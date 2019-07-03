package com.stylefeng.guns.rest.modular.film.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.modular.film.service.IMoocOrderTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author cutecoder
 * @since 2019-06-10
 */
@Service(interfaceClass = IMoocOrderTService.class)
@Component
public class MoocOrderTServiceImpl extends ServiceImpl<MoocOrderTMapper, MoocOrderT> implements IMoocOrderTService {

}
