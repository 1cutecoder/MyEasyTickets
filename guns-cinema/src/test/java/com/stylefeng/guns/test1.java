package com.stylefeng.guns;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeBrandDictT;
import com.stylefeng.guns.rest.modular.cinema.impl.MtimeBrandDictTServiceImpl;
import com.stylefeng.guns.rest.modular.cinema.service.IMtimeBrandDictTService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cute coder
 * 2019/6/13 16:08
 */
public class test1 {

    @Test
    public void test1(){
        IMtimeBrandDictTService brandDictTService = new MtimeBrandDictTServiceImpl();
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            arrayList.add(i);
        }
        List<MtimeBrandDictT> brands = brandDictTService.selectList(new EntityWrapper<MtimeBrandDictT>().eq("UUID","10"));
        System.out.println("brands = " + brands);
    }
}
