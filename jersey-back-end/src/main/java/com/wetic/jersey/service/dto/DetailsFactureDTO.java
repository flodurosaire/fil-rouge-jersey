package com.wetic.jersey.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wetic.jersy.domain.DetailsFacture} entity.
 */
public class DetailsFactureDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer qteProduit;

    private Long factureId;

    private Long produitId;

    private String produitName;

    private String description;

    private Float produitPrix;

    public Float getProduitPrix() {
        return produitPrix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProduitPrix(Float produitPrix) {
		this.produitPrix = produitPrix;
	}

	public String getProduitName() {
		return produitName;
	}

	public void setProduitName(String produitName) {
		this.produitName = produitName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQteProduit() {
        return qteProduit;
    }

    public void setQteProduit(Integer qteProduit) {
        this.qteProduit = qteProduit;
    }

    public Long getFactureId() {
        return factureId;
    }

    public void setFactureId(Long factureId) {
        this.factureId = factureId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetailsFactureDTO detailsFactureDTO = (DetailsFactureDTO) o;
        if (detailsFactureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailsFactureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailsFactureDTO{" +
            "id=" + getId() +
            ", qteProduit=" + getQteProduit() +
            ", facture=" + getFactureId() +
            ", produit=" + getProduitId() +
            "}";
    }
}
