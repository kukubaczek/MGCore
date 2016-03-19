package net.kukubaczek.minigames.core.tasks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;

import net.kukubaczek.minigames.core.MGCore;
import net.kukubaczek.minigames.core.general.ArenaKey;
import net.kukubaczek.minigames.core.general.ArenaObject;
import net.kukubaczek.minigames.core.general.ArenaStatus;

public class RefreshRedisArenas extends BukkitRunnable {

    private MGCore plugin;
    public RefreshRedisArenas(MGCore plugin){
    	this.plugin = plugin;
    }
    
    private int lastOnline = -1;
    private ArenaStatus  lastStatus = ArenaStatus.OFFLINE;

    @Override
    public void run() {
    	if(MGCore.uploadThisArena){
            MGCore.log("[SAVE] Rozpoczynanie zapisu areny...");

            final Map<String, String> data = new HashMap<>();
            ArenaObject a = plugin.getArena(); 
        	if(lastOnline != -1 && a.getPlayersOnline() == lastOnline){
        		System.out.println("1. " + lastStatus.toString() + " A: " + a.getStatus().toString());
        		if(lastStatus != ArenaStatus.OFFLINE && lastStatus.equals(a.getStatus())){
        			MGCore.log("[SAVE] Przerwano zapis areny: STATUS SIE NIE ZMIENIL. (" + lastOnline + " == " + a.getPlayersOnline()+")");
        			return;
        		}
        	}
            synchronized (a) {
                data.put(ArenaKey.SERVER.toString(), a.getBungeeServer());
                data.put(ArenaKey.STATUS.toString(), a.getStatus().toString());
                data.put(ArenaKey.MINIGRA.toString(), a.getMinigameName());
                data.put(ArenaKey.MAPA.toString(), a.getMapName());
                data.put(ArenaKey.ID_ARENY.toString(), String.valueOf(a.getArenaID()));
                data.put(ArenaKey.ONLINE.toString(), String.valueOf(a.getPlayersOnline()));
                data.put(ArenaKey.MAX_ONLINE.toString(), String.valueOf(a.getMaxPlayers()));
                data.put(ArenaKey.LAST_SYNC.toString(), String.valueOf(System.currentTimeMillis()));
            }
            plugin.getRedis().hmset(a.getPrefix() + a.getArenaID(), data);
            lastOnline=a.getPlayersOnline();
            lastStatus = a.getStatus();
            MGCore.log("[SAVE] Zapisano status areny (" + a.getPrefix() + a.getArenaID() + ")");
    	}
    }
}
