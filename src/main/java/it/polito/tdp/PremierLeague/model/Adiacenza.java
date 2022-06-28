package it.polito.tdp.PremierLeague.model;

public class Adiacenza {
	
	private int id1;
	private int id2;
	private double e1;
	private double e2;
	public Adiacenza(int id1, int id2, double e1, double e2) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.e1 = e1;
		this.e2 = e2;
	}
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}
	public double getE1() {
		return e1;
	}
	public void setE1(double e1) {
		this.e1 = e1;
	}
	public double getE2() {
		return e2;
	}
	public void setE2(double e2) {
		this.e2 = e2;
	}

}
