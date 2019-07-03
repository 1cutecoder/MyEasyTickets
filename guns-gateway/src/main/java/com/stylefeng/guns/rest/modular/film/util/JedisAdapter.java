package com.stylefeng.guns.rest.modular.film.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisAdapter implements InitializingBean {

    JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        //jedis = new Jedis("localhost");
        pool = new JedisPool();
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String hget(String key,String field) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hget(key,field);
        } catch (Exception e) {
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long hset(String key,String field, String value) {
        Jedis jedis = null;Long hset = (long)0;
        try {
            jedis = pool.getResource();
            hset = jedis.hset(key, field, value);

        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return hset;
    }
    public Long hdel(String key,String field) {
        Jedis jedis = null;
        Long hdel = (long)0;
        try {
            jedis = pool.getResource();
            hdel = jedis.hdel(key, field);
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return hdel;
    }

    public Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis;
        } catch (Exception e) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return jedis;
    }
}