package net.kukubaczek.minigames.core.tasks;

import java.util.HashMap;
import java.util.Map;

import net.kukubaczek.minigames.core.Main;
import net.kukubaczek.minigames.core.general.ArenaKey;
import net.kukubaczek.minigames.core.general.ArenaObject;
import redis.clients.jedis.Jedis;

public class RefreshRedisArenas implements Runnable{

	@Override
	public void run() {
		
        try (Jedis j = Main.pool.getResource()) {
            final Map<String, String> data = new HashMap<>();
            ArenaObject a = Main.arena;
            synchronized (a){
                data.put(ArenaKey.SERVER.toString(), a.getBungeeServer());
                data.put(ArenaKey.STATUS.toString(), a.getStatus().toString());
                data.put(ArenaKey.MINIGRA.toString(), a.getMinigameName());
                data.put(ArenaKey.MAPA.toString(), a.getMapName());
                data.put(ArenaKey.ID_ARENY.toString(), String.valueOf(a.getArenaID()));
                data.put(ArenaKey.ONLINE.toString(), String.valueOf(a.getPlayersOnline()));
                data.put(ArenaKey.MAX_ONLINE.toString(), String.valueOf(a.getMaxPlayers()));
            }
            j.hmset(a.getPrefix() + a.getArenaID(), data);
        }
		
	}
	
	

}
