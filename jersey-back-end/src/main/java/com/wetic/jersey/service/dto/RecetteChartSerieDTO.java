package com.wetic.jersey.service.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


public class RecetteChartSerieDTO {

	private int mois;

	private Long valeurMois;
	public int getMois() {
		return mois;
	}
	public void setMois(int mois) {
		this.mois = mois;
	}
	public Long getValeurMois() {
		return valeurMois;
	}
	public void setValeurMois(Long valeurMois) {
		this.valeurMois = valeurMois;
	}
	public RecetteChartSerieDTO(Long valeurMois, int mois ) {
		super();
		this.mois = mois;
		this.valeurMois = valeurMois;
	}
	public RecetteChartSerieDTO(Double valeurMois, int mois ) {
		super();
		this.mois = mois;
		// temp solution
		this.valeurMois = Math.round(valeurMois);
	}
	

}
