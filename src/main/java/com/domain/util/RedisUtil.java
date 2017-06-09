package com.domain.util;

import redis.clients.jedis.Jedis;

/**
 * Created by sunxin(hysx8@sina.com) on 2017/6/9.
 */
public class RedisUtil {


    private static Jedis jedis;

    public void setJedis(Jedis jedis) {
        RedisUtil.jedis = jedis;
    }

    public static void set(String key, String value){
        jedis.set(key, value);
    }

    public static String get(String key){
        return jedis.get(key);
    }

}
