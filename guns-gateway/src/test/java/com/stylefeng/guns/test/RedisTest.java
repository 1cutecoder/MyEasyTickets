package com.stylefeng.guns.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * Created by cute coder
 * 2019/6/6 23:12
 */
public class RedisTest {
    @Test
    public void test1(){
        Jedis jedis = new Jedis();
        jedis.hset("haha","h1","1");
        String hget = jedis.hget("haha", "h1");
        System.out.println("hget = " + hget);
    }
    @Test
    public void test2(){
        Date date = new Date();
        System.out.println("date = " + date);
    }
}
