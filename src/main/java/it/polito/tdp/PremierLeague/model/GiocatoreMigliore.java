package it.polito.tdp.PremierLeague.model;

public class GiocatoreMigliore {
	
	private Player player;
	private double deltaMigliore;
	public GiocatoreMigliore(Player player, double deltaMigliore) {
		super();
		this.player = player;
		this.deltaMigliore = deltaMigliore;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public double getDeltaMigliore() {
		return deltaMigliore;
	}
	public void setDeltaMigliore(double deltaMigliore) {
		this.deltaMigliore = deltaMigliore;
	}
	@Override
	public String toString() {
		return "GiocatoreMigliore [player=" + player + ", deltaMigliore=" + deltaMigliore + "]";
	}

}
