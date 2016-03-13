package net.kukubaczek.minigames.core.tasks;

import lombok.AllArgsConstructor;
import net.kukubaczek.minigames.core.MGCore;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class RefreshPlayersOnline extends BukkitRunnable {

    private MGCore plugin;

    @Override
    public void run() {
        plugin.getArena().refreshPlayersOnline();
    }

}
