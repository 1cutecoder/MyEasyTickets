package com.stylefeng.guns.redis;

import com.alibaba.druid.support.json.JSONUtils;
import com.stylefeng.guns.rest.modular.film.util.JedisAdapter;
import com.stylefeng.guns.rest.modular.film.util.Json2Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.Map;

/**
 * Created by cute coder
 * 2019/6/9 20:43
 */
@SpringBootTest
public class JsonToRedis {



    @Test
    public void readJsonFile() {
        String jsonStr = "";
        try {
            File jsonFile = new File("C:\\Users\\A\\Desktop\\05 接口文档\\订单模块接口文档\\cgs.json");
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            //Jedis jedis = new Jedis();
            //Long hset = jedis.hset("hall_seats", "mtime_hall_dict_t", jsonStr);
            Object parse = JSONUtils.parse(jsonStr);
            System.out.println("parse = " + parse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

