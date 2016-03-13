package net.kukubaczek.minigames.core.general;

public class ArenaObject {

	String bungeeServer;
	ArenaStatus status;
	String prefixMinigry;
	String minigra;
	String nazwaAreny;
	String mapa;
	
	public ArenaObject(	String bungeeServer, String prefixMinigry, String minigra, String nazwaAreny, String mapa){
		this.bungeeServer = bungeeServer;
		status =  ArenaStatus.IN_LOBBY;
		this.prefixMinigry = prefixMinigry;
		this.minigra = minigra;
		this.nazwaAreny = nazwaAreny;
		this.mapa = mapa;
		
	}
	
}
