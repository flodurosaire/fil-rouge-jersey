package com.wetic.jersey.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DetailsFacture.
 */
@Entity
@Table(name = "details_facture")
public class DetailsFacture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "qte_produit", nullable = false)
    private Integer qteProduit;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("detailsFactures")
    private Facture facture;

    @ManyToOne
    @JsonIgnoreProperties("detailsFactures")
    private Produit produit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public DetailsFacture(){}
    // public DetailsFacture(Integer qteProduit, String description, Facture facture, Produit produit){
    //     this.qteProduit = qteProduit;
    //     this.description = description;
    //     this.facture = facture;
    //     this.produit = produit;
    // }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQteProduit() {
        return qteProduit;
    }

    public DetailsFacture qteProduit(Integer qteProduit) {
        this.qteProduit = qteProduit;
        return this;
    }

    public void setQteProduit(Integer qteProduit) {
        this.qteProduit = qteProduit;
    }

    public DetailsFacture description(String description) {
        this.description = description;
        return this;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Facture getFacture() {
        return facture;
    }

    public DetailsFacture facture(Facture facture) {
        this.facture = facture;
        return this;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Produit getProduit() {
        return produit;
    }

    public DetailsFacture produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailsFacture)) {
            return false;
        }
        return id != null && id.equals(((DetailsFacture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DetailsFacture{" +
            "id=" + getId() +
            ", qteProduit=" + getQteProduit() +
            "}";
    }
}
