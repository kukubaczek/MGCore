package net.kukubaczek.minigames.core.tasks;

import net.kukubaczek.minigames.core.Main;

public class RefreshPlayersOnline implements Runnable{

	@Override
	public void run() {
		
		Main.arena.refreshPlayersOnline();
		
	}

}
