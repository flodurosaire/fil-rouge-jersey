package com.wetic.jersey.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wetic.jersy.domain.Depense} entity.
 */
public class DepenseDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    @NotNull
    private Float montant;

    private String description;

    @NotNull
    private ZonedDateTime dateFacturation;
    
    private String typeDepenseName;

    public String getTypeDepenseName() {
		return typeDepenseName;
	}

	public void setTypeDepenseName(String typeDepenseName) {
		this.typeDepenseName = typeDepenseName;
	}

	private Long typeDepenseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDateFacturation() {
        return dateFacturation;
    }

    public void setDateFacturation(ZonedDateTime dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public Long getTypeDepenseId() {
        return typeDepenseId;
    }

    public void setTypeDepenseId(Long typeDepenseId) {
        this.typeDepenseId = typeDepenseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepenseDTO depenseDTO = (DepenseDTO) o;
        if (depenseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depenseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepenseDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", montant=" + getMontant() +
            ", description='" + getDescription() + "'" +
            ", dateFacturation='" + getDateFacturation() + "'" +
            ", typeDepense=" + getTypeDepenseId() +
            "}";
    }
}
