package com.wetic.jersey.service.dto;

public class ClientAddedByMonthOutput {

	private int nbrClientAjoute;
	public int getNbrClientAjoute() {
		return nbrClientAjoute;
	}
	public void setNbrClientAjoute(int nbrClientAjoute) {
		this.nbrClientAjoute = nbrClientAjoute;
	}
	public int getMois() {
		return mois;
	}
	public void setMois(int mois) {
		this.mois = mois;
	}
	private int mois;
	
}
