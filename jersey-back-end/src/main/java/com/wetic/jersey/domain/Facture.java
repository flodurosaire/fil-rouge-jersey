package com.wetic.jersey.domain;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * not an ignored comment
 */
@Entity
@Table(name = "facture")
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_facturation", nullable = false)
    private ZonedDateTime dateFacturation;

    @ManyToOne
    @JsonIgnoreProperties("factures")
    private Client client;

    @OneToMany(mappedBy = "facture")
    private Set<DetailsFacture> detailsFacture = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Facture (){}
    // public Facture (ZonedDateTime dateFacturation, Client client ){
    //     this.dateFacturation = dateFacturation;
    //     this.client = client;
    // }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateFacturation() {
        return dateFacturation;
    }

    public Facture dateFacturation(ZonedDateTime dateFacturation) {
        this.dateFacturation = dateFacturation;
        return this;
    }

    public void setDateFacturation(ZonedDateTime dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public Client getClient() {
        return client;
    }

    public Facture client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<DetailsFacture> getFetailsFacture() {
        return detailsFacture;
    }

    public Facture detailsFacture(Set<DetailsFacture> detailsFactures) {
        this.detailsFacture = detailsFactures;
        return this;
    }

    public Facture addDetailsFacture(DetailsFacture detailsFacture) {
        this.detailsFacture.add(detailsFacture);
        detailsFacture.setFacture(this);
        return this;
    }

    public Facture removeDetailsFacture(DetailsFacture detailsFacture) {
        this.detailsFacture.remove(detailsFacture);
        detailsFacture.setFacture(null);
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
        if (!(o instanceof Facture)) {
            return false;
        }
        return id != null && id.equals(((Facture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", dateFacturation='" + getDateFacturation() + "'" +
            "}";
    }
}
