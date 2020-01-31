package com.wetic.jersey.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeDepense.
 */
@Entity
@Table(name = "type_depense")
public class TypeDepense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "typeDepense")
    private Set<Depense> depenses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public TypeDepense (){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public TypeDepense libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public TypeDepense description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Depense> getDepenses() {
        return depenses;
    }

    public TypeDepense depenses(Set<Depense> depenses) {
        this.depenses = depenses;
        return this;
    }

    public TypeDepense addDepense(Depense depense) {
        this.depenses.add(depense);
        depense.setTypeDepense(this);
        return this;
    }

    public TypeDepense removeDepense(Depense depense) {
        this.depenses.remove(depense);
        depense.setTypeDepense(null);
        return this;
    }

    public void setDepenses(Set<Depense> depenses) {
        this.depenses = depenses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeDepense)) {
            return false;
        }
        return id != null && id.equals(((TypeDepense) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeDepense{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
