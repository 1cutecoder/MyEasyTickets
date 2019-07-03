package com.stylefeng.guns.rest.modular.auth.validator.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.modular.auth.validator.dto.Credence;
import com.stylefeng.guns.rest.modular.film.service.MtimeUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 直接验证账号密码是不是admin
 *
 * @author fengshuonan
 * @date 2017-08-23 12:34
 */
@Service
public class UserValidator implements IReqValidator {

    @Reference(check = false)
    MtimeUserService mtimeUserService;

    @Override
    public boolean validate(Credence credence) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUsername(credence.getCredenceName());
        String password = credence.getCredenceCode();
        if (password!=null){
            password= MD5Util.encrypt(password);

        }
        List<MtimeUserT> users = mtimeUserService.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", credence.getCredenceName()));
        if (users != null && users.size() > 0 && users.get(0).getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
