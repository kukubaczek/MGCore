package net.kukubaczek.minigames.core;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import net.kukubaczek.minigames.core.general.ArenaObject;
import net.kukubaczek.minigames.core.tasks.RefreshPlayersOnline;
import net.kukubaczek.minigames.core.tasks.RefreshRedisArenas;

public class MGCore extends JavaPlugin {

    public static final String PREFIX = "grav_";

    @Getter
    private static MGCore instance;

    @Getter
    private ArenaObject arena;

    @Getter
    private Redis redis;

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

        log("Laczenie z baza Redis...");
        redis = new Redis(this);
        arena = new ArenaObject("gravity-1", PREFIX, "Gravity", 1, "Brak");

        redis.lpush(PREFIX + "arenalist", String.valueOf(arena.getArenaID()));
        log("Pomyslnie zaaktywowano arene w redisie!");

        new RefreshRedisArenas(this).runTaskTimerAsynchronously(this, 600L, 10L);
        new RefreshPlayersOnline(this).runTaskTimer(this, 60L, 60L);

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
		// TODO Auto-generated method stub
		return arena;
	}

	public Redis getRedis() {
		return redis;
	}

}
