package net.kukubaczek.minigames.core.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import lombok.AllArgsConstructor;
import net.kukubaczek.minigames.core.MGCore;

@AllArgsConstructor
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
