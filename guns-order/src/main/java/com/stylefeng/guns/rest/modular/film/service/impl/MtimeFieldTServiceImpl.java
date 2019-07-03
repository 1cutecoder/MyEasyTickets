package com.stylefeng.guns.rest.modular.film.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFieldT;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFieldTMapper;
import com.stylefeng.guns.rest.modular.film.service.IMtimeFieldTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 放映场次表 服务实现类
 * </p>
 *
 * @author cutecoder
 * @since 2019-06-10
 */
@Service(interfaceClass = IMtimeFieldTService.class )
@Component
public class MtimeFieldTServiceImpl extends ServiceImpl<MtimeFieldTMapper, MtimeFieldT> implements IMtimeFieldTService {

}
