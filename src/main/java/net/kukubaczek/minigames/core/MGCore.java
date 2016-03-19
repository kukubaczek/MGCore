package net.kukubaczek.minigames.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.kukubaczek.minigames.core.general.ArenaKey;
import net.kukubaczek.minigames.core.general.ArenaObject;
import net.kukubaczek.minigames.core.tasks.RefreshPlayersOnline;
import net.kukubaczek.minigames.core.tasks.RefreshRedisArenas;

public class MGCore extends JavaPlugin {

    public static final String PREFIX = "grav_";

    private static MGCore instance;

    private ArenaObject arena;

    private Redis redis;
    
    public static boolean loadArenaList;
    public static boolean uploadThisArena;

    public Map<String, ArrayList<ArenaObject>> gry = new HashMap<String, ArrayList<ArenaObject>>();
    
    public static void log(String msg) {
        System.out.println("[MG_Core] " + msg);
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        log("Ladowanie configu...");

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        this.loadArenaList = this.getConfig().getBoolean("loadArenaList");
        this.uploadThisArena = this.getConfig().getBoolean("uploadThisArena");
        
        log("Laczenie z baza Redis...");
        redis = new Redis(this);
        if(uploadThisArena) arena = new ArenaObject("gravity-1", PREFIX, "Gravity", 1, "Brak");

        redis.lpush(PREFIX + "arenalist", String.valueOf(arena.getArenaID()));
        log("Pomyslnie zaaktywowano arene w redisie!");

        new RefreshRedisArenas(this).runTaskTimerAsynchronously(this, 100L,100L);
        new RefreshPlayersOnline(this).runTaskTimer(this, 100L, 100L);
        
        log("Zaladowano!");
    }

    @Override
    public void onDisable() {
        log("Dezaktywacja areny w redisie!");
        redis.lrem(PREFIX + "arenalist", -1, String.valueOf(arena.getArenaID()));
        log("Rozlaczanie z baza danych redis!");
        redis.close();
    }

	public ArenaObject getArena() {
		return arena;
	}

	public Redis getRedis() {
		return redis;
	}

}
