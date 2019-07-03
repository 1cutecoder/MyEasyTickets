package com.stylefeng.guns.rest.modular.film.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;

import java.util.List;

/**
 * Created by cute coder
 * 2019/6/5 15:19
 */
public interface MtimeUserService {

    int insert(MtimeUserT mtimeUserT);

    MtimeUserT selectOne(MtimeUserT mtimeUserT);

    List<MtimeUserT> selectList(Wrapper<MtimeUserT> userName);

    int update(MtimeUserT mtimeUserT);
}
