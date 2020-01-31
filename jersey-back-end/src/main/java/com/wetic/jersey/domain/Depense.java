package com.wetic.jersey.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.wetic.jersey.domain.TypeDepense;


/**
 * A Depense.
 */
@Entity
@Table(name = "depense")
public class Depense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Float montant;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "date_facturation", nullable = false)
    private ZonedDateTime dateFacturation;

    @ManyToOne
    @JsonIgnoreProperties("depenses")
    private TypeDepense typeDepense;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Depense(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Depense libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Float getMontant() {
        return montant;
    }

    public Depense montant(Float montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public String getDescription() {
        return description;
    }

    public Depense description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDateFacturation() {
        return dateFacturation;
    }

    public Depense dateFacturation(ZonedDateTime dateFacturation) {
        this.dateFacturation = dateFacturation;
        return this;
    }

    public void setDateFacturation(ZonedDateTime dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public TypeDepense getTypeDepense() {
        return typeDepense;
    }

    public Depense typeDepense(TypeDepense typeDepense) {
        this.typeDepense = typeDepense;
        return this;
    }

    public void setTypeDepense(TypeDepense typeDepense) {
        this.typeDepense = typeDepense;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Depense)) {
            return false;
        }
        return id != null && id.equals(((Depense) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Depense{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", montant=" + getMontant() +
            ", description='" + getDescription() + "'" +
            ", dateFacturation='" + getDateFacturation() + "'" +
            "}";
    }
}
