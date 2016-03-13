package net.kukubaczek.minigames.core;

import org.bukkit.plugin.java.JavaPlugin;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Main extends JavaPlugin{
	
	
	public static JedisPool pool;
	
	public static Jedis jedis;

	@Override
	public void onEnable(){
		log("Ladowanie configu...");
		
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
		
        String ip = getConfig().getString("redis.ip");
        int port = getConfig().getInt("redis.port");
        String password = getConfig().getString("redis.password");
		
        log("Laczenie z baza Redis...");
        if (password == null || password.equals(""))
            pool = new JedisPool(new JedisPoolConfig(), ip, port, 0);
        else
            pool = new JedisPool(new JedisPoolConfig(), ip, port, 0, password);

        log("Zaladowano!");
	}
	
	@Override
	public void onDisable() {
	    pool.destroy();
	}
	
	public static void log(String msg){
		System.out.println("[MG_Core] " + msg);
	}

}
