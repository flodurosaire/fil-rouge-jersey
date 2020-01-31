package com.wetic.jersey.domain;




import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * Task entity.
 * @author The JHipster team.
 */
@Entity
@Table(name = "produit")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "prix", nullable = false)
    private Float prix;

    @Column(name = "qte_stock")
    private Integer qteStock;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "produit")
    private Set<DetailsFacture> detailsFacture = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Produit(){

    }
    // public Produit(String libelle , Float prix,Integer qteStock ){
    //     this.libelle = libelle;
    //     this.prix = prix;
    //     this.qteStock = qteStock;

    // }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Produit libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Float getPrix() {
        return prix;
    }

    public Produit prix(Float prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Integer getQteStock() {
        return qteStock;
    }

    public Produit qteStock(Integer qteStock) {
        this.qteStock = qteStock;
        return this;
    }

    public void setQteStock(Integer qteStock) {
        this.qteStock = qteStock;
    }

    public String getDescription() {
        return description;
    }

    public Produit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DetailsFacture> getDetailsFacture() {
        return detailsFacture;
    }

    public Produit detailsFacture(Set<DetailsFacture> detailsFactures) {
        this.detailsFacture = detailsFactures;
        return this;
    }

    public Produit addDetailsFacture(DetailsFacture detailsFacture) {
        this.detailsFacture.add(detailsFacture);
        detailsFacture.setProduit(this);
        return this;
    }

    public Produit removeDetailsFacture(DetailsFacture detailsFacture) {
        this.detailsFacture.remove(detailsFacture);
        detailsFacture.setProduit(null);
        return this;
    }

    public void setDetailsFacture(Set<DetailsFacture> detailsFactures) {
        this.detailsFacture = detailsFactures;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", prix=" + getPrix() +
            ", qteStock=" + getQteStock() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
