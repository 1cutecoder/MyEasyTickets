package com.stylefeng.guns.rest.modular.cinema.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeAreaDictTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeAreaDictT;
import com.stylefeng.guns.rest.modular.cinema.service.IMtimeAreaDictTService;
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
@Component
@Service(interfaceClass = IMtimeAreaDictTService.class)
public class MtimeAreaDictTServiceImpl extends ServiceImpl<MtimeAreaDictTMapper, MtimeAreaDictT> implements IMtimeAreaDictTService {

}
