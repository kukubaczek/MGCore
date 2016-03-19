package net.kukubaczek.minigames.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import net.kukubaczek.minigames.core.general.ArenaObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {

    private MGCore plugin;
    private JedisPool pool;
    
    public Redis(MGCore plugin) {
        this.plugin = plugin;

        //String ip = plugin.getConfig().getString("redis.ip");
        //int port = plugin.getConfig().getInt("redis.port");
        //String password = plugin.getConfig().getString("redis.password");
        
        String ip = "localhost";
        int port = 6379;
        String password = null;

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
    
    public List<String> lrange(String key, long start, long end) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Redis exception", e);
        }
		return null;
    }

    public void hmset(String key, Map<String, String> values) {
        try (Jedis jedis = pool.getResource()){
            jedis.hmset(key, values);
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Redis exception", e);
        }
    }
    
    public Map<String, String> hgetall(String key) {
        try (Jedis jedis = pool.getResource()){
            return jedis.hgetAll(key);
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Redis exception", e);
        }
		return null;
    }

    @SuppressWarnings("Remember to call jedis.close()")
    public Jedis getUnsafe() {
        return pool.getResource();
    }

    public void close() {
        pool.destroy();
    }
}
