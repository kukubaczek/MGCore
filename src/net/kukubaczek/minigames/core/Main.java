package net.kukubaczek.minigames.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.kukubaczek.minigames.core.general.ArenaObject;
import net.kukubaczek.minigames.core.tasks.RefreshPlayersOnline;
import net.kukubaczek.minigames.core.tasks.RefreshRedisArenas;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Main extends JavaPlugin{
	
	
	public static JedisPool pool;
	
	public static Jedis jedis;
	
	public static ArenaObject arena;
	
	public static String prefix = "grav_";

	@Override
	public void onEnable(){
		log("Ladowanie configu...");
		
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
		
        String ip = getConfig().getString("redis.ip");
        int port = getConfig().getInt("redis.port");
        String password = getConfig().getString("redis.password");
        
        arena = new ArenaObject( "gravity-1", prefix, "Gravity", 1, "Brak");
		
        log("Laczenie z baza Redis...");
        if (password == null || password.equals(""))
            pool = new JedisPool(new JedisPoolConfig(), ip, port, 0);
        else
            pool = new JedisPool(new JedisPoolConfig(), ip, port, 0, password);
        new BukkitRunnable() {
            @Override
            public void run() {
                jedis = pool.getResource();
                pool.returnResource(jedis);
                log("Polaczono z baza Redis!");
            }
        }.runTaskAsynchronously(this);
        
        jedis.lpush(prefix+"arenalist", String.valueOf(arena.getArenaID()));
        log("Pomyslnie zaaktywowano arene w redisie!");
        
        log("Tworzenie watkow...");
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new RefreshRedisArenas(), 100L, 100L);
        Bukkit.getScheduler().runTaskTimer(this, new RefreshPlayersOnline(), 20L, 20L);
        
        log("Zaladowano!");
	}
	
	@Override
	public void onDisable() {
		log("Dezaktywacja areny w redisie!");
		jedis.lrem(prefix+"arenalist", -1, String.valueOf(arena.getArenaID()));
		log("Rozlaczanie z baza danych redis!");
	    pool.destroy();
	}
	
	public static void log(String msg){
		System.out.println("[MG_Core] " + msg);
	}

}
