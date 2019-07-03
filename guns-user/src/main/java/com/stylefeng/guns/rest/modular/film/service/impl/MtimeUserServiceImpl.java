package com.stylefeng.guns.rest.modular.film.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.stylefeng.guns.rest.modular.film.service.MtimeUserService;

import java.util.List;

/**
 * Created by cute coder
 * 2019/6/5 15:39
 */
@Service(interfaceClass = MtimeUserService.class)
@Component
public class MtimeUserServiceImpl implements MtimeUserService {
    @Autowired
    MtimeUserTMapper mtimeUserTMapper;

    @Override
    public int insert(MtimeUserT mtimeUserT) {
        Integer insert = mtimeUserTMapper.insert(mtimeUserT);
        return insert;
    }

    @Override
    public MtimeUserT selectOne(MtimeUserT mtimeUserT) {
        MtimeUserT selectOne = mtimeUserTMapper.selectOne(mtimeUserT);
        return selectOne;
    }

    @Override
    public List<MtimeUserT> selectList(Wrapper<MtimeUserT> user) {
        List<MtimeUserT> mtimeUserTS = mtimeUserTMapper.selectList(user);
        return mtimeUserTS;
    }

    @Override
    public int update(MtimeUserT mtimeUserT) {
        Integer updateById = mtimeUserTMapper.updateById(mtimeUserT);
        return updateById;
    }

}
