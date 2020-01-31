package com.wetic.jersey.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wetic.jersy.domain.Facture} entity.
 */
@ApiModel(description = "not an ignored comment")
public class FactureDTO implements Serializable {

    private Long id;

    @NotNull 
    private ZonedDateTime dateFacturation;


    private Long clientId;
    
    private String clientName;
    

    public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateFacturation() {
        return dateFacturation;
    }

    public void setDateFacturation(ZonedDateTime dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FactureDTO factureDTO = (FactureDTO) o;
        if (factureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), factureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FactureDTO{" +
            "id=" + getId() +
            ", dateFacturation='" + getDateFacturation() + "'" +
            ", client=" + getClientId() +
            "}";
    }
}
