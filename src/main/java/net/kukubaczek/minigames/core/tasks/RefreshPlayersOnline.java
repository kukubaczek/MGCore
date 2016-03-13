package net.kukubaczek.minigames.core.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import net.kukubaczek.minigames.core.MGCore;

public class RefreshPlayersOnline extends BukkitRunnable {
    
    private MGCore plugin;
    public RefreshPlayersOnline(MGCore plugin){
    	this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getArena().refreshPlayersOnline();
    }

}
