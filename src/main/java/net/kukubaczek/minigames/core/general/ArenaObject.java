package net.kukubaczek.minigames.core.general;

import org.bukkit.Bukkit;

public class ArenaObject {

    String bungeeServer;
    ArenaStatus status;
    String prefixMinigry;

    String minigra;
    String mapa;

    int arenaID;
    int playersOnline;
    int maxPlayers;

    public ArenaObject(String bungeeServer, String prefixMinigry, String minigra, int arenaID, String mapa) {
        this.bungeeServer = bungeeServer;
        status = ArenaStatus.IN_LOBBY;
        this.prefixMinigry = prefixMinigry;
        this.minigra = minigra;
        this.arenaID = arenaID;
        this.mapa = mapa;
        maxPlayers = Bukkit.getMaxPlayers();
    }

    public String getBungeeServer() {
        return this.bungeeServer;
    }

    public ArenaStatus getStatus() {
        return this.status;
    }

    public String getPrefix() {
        return this.prefixMinigry;
    }

    public String getMinigameName() {
        return this.minigra;
    }

    public String getMapName() {
        return this.mapa;
    }

    public int getPlayersOnline() {
        return this.playersOnline;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public int getArenaID() {
        return this.arenaID;
    }

    public void refreshPlayersOnline() {
        this.playersOnline = Bukkit.getOnlinePlayers().size();
    }

}
