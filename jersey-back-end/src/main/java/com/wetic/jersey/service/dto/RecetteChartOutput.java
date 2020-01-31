package com.wetic.jersey.service.dto;

import java.util.ArrayList;

public class RecetteChartOutput {
	private ArrayList<RecetteChartSerieDTO> CLientAddedByMonth;
	private ArrayList<RecetteChartSerieDTO> SumDepensesByMonth;
	public ArrayList<RecetteChartSerieDTO> getCLientAddedByMonth() {
		return CLientAddedByMonth;
	}
	public void setCLientAddedByMonth(ArrayList<RecetteChartSerieDTO> cLientAddedByMonth) {
		CLientAddedByMonth = cLientAddedByMonth;
	}
	public ArrayList<RecetteChartSerieDTO> getSumDepensesByMonth() {
		return SumDepensesByMonth;
	}
	public void setSumDepensesByMonth(ArrayList<RecetteChartSerieDTO> sumDepensesByMonth) {
		SumDepensesByMonth = sumDepensesByMonth;
	}
	public ArrayList<RecetteChartSerieDTO> getSumFacturesByMonth() {
		return SumFacturesByMonth;
	}
	public void setSumFacturesByMonth(ArrayList<RecetteChartSerieDTO> sumFacturesByMonth) {
		SumFacturesByMonth = sumFacturesByMonth;
	}
	private ArrayList<RecetteChartSerieDTO> SumFacturesByMonth;
	
	
}
