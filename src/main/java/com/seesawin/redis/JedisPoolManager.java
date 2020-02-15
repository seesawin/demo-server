package com.seesawin.redis;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolManager {
    private volatile static JedisPoolManager manager;
    private final JedisPool pool;

    private JedisPoolManager() {
        // 创建jedis池配置实例
        JedisPoolConfig config = new JedisPoolConfig();

        // 设置池配置项值
        String maxTotal = "4";
        config.setMaxTotal(Integer.parseInt(maxTotal));

        String maxIdle = "4";
        config.setMaxIdle(Integer.parseInt(maxIdle));

        String minIdle = "1";
        config.setMinIdle(Integer.parseInt(minIdle));

        String maxWaitMillis = "1024";
        config.setMaxWaitMillis(Long.parseLong(maxWaitMillis));

        String testOnBorrow = "true";
        config.setTestOnBorrow("true".equals(testOnBorrow));

        String testOnReturn = "true";
        config.setTestOnReturn("true".equals(testOnReturn));

        String server = "127.0.0.1:6379";
        if (StringUtils.isEmpty(server)) {
            throw new IllegalArgumentException("JedisPool redis.server is empty!");
        }

        String[] host_arr = server.split(",");
        if (host_arr.length > 1) {
            throw new IllegalArgumentException("JedisPool redis.server length > 1");
        }

        String[] arr = host_arr[0].split(":");

        // 根据配置实例化jedis池
        System.out.println("***********init JedisPool***********");
        System.out.println("host->" + arr[0] + ",port->" + arr[1]);

        pool = new JedisPool(config, arr[0], Integer.parseInt(arr[1]), 50000, "The0ne");

    }

    public static JedisPoolManager getMgr() {
        if (manager == null) {
            synchronized (JedisPoolManager.class) {
                if (manager == null) {
                    manager = new JedisPoolManager();
                }
            }
        }
        return manager;
    }

    public Jedis getResource() {

        return pool.getResource();
    }

    public void destroy() {
        // when closing your application:
        pool.destroy();
    }

    public void close() {
        pool.close();
    }
}
