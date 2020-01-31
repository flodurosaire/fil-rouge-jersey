package com.wetic.jersey.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wetic.jersy.domain.Produit} entity.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
public class StatsDTO implements Serializable {

	private long countProduit;
	public long getCountProduit() {
		return countProduit;
	}
	public void setCountProduit(long countProduit) {
		this.countProduit = countProduit;
	}
	public long getCountFacture() {
		return countFacture;
	}
	public void setCountFacture(long countFacture) {
		this.countFacture = countFacture;
	}
	public long getCountClient() {
		return countClient;
	}
	public void setCountClient(long countClient) {
		this.countClient = countClient;
	}
	public long getSumDepense() {
		return sumDepense;
	}
	public void setSumDepense(long sumDepense) {
		this.sumDepense = sumDepense;
	}
	private long countFacture;
	private long countClient;
	private long sumDepense;
    
}
