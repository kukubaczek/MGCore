package net.kukubaczek.minigames.core;

import java.util.Map;
import java.util.logging.Level;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {

    private MGCore plugin;
    private JedisPool pool;

    public Redis(MGCore plugin) {
        this.plugin = plugin;

        String ip = plugin.getConfig().getString("redis.ip");
        int port = plugin.getConfig().getInt("redis.port");
        String password = plugin.getConfig().getString("redis.password");

        if (password == null || password.equals("")) {
            pool = new JedisPool(new JedisPoolConfig(), ip, port, 0);
        } else {
            pool = new JedisPool(new JedisPoolConfig(), ip, port, 0, password);
        }
    }

    //TODO other methods
    public void lpush(String key, String... data) {
        try (Jedis jedis = pool.getResource()) {
            jedis.lpush(key, data);
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Redis exception", e);
        }
    }

    public void lrem(String key, long count, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.lrem(key, count, value);
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Redis exception", e);
        }
    }

    public void hmset(String key, Map<String, String> values) {
        try (Jedis jedis = pool.getResource()){
            jedis.hmset(key, values);
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Redis exception", e);
        }
    }

    @SuppressWarnings("Remember to call jedis.close()")
    public Jedis getUnsafe() {
        return pool.getResource();
    }

    public void close() {
        pool.destroy();
    }
}
