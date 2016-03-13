package net.kukubaczek.minigames.core.tasks;

import net.kukubaczek.minigames.core.MG_Core;

public class RefreshPlayersOnline implements Runnable{

	@Override
	public void run() {
		
		MG_Core.arena.refreshPlayersOnline();
		
	}

}
