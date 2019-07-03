package com.stylefeng.guns.rest.modular.cinema.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeBrandDictTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeBrandDictT;
import com.stylefeng.guns.rest.modular.cinema.service.IMtimeBrandDictTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 品牌信息表 服务实现类
 * </p>
 *
 * @author cutecoder
 * @since 2019-06-13
 */
@Service(interfaceClass = IMtimeBrandDictTService.class)
@Component
public class MtimeBrandDictTServiceImpl extends ServiceImpl<MtimeBrandDictTMapper, MtimeBrandDictT> implements IMtimeBrandDictTService {

}
