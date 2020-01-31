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
public class FactureClientDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime dateFacturation;


    private Long clientId;

    private String clientDisplayName;
    
    public String getClientDisplayName() {
    	return clientDisplayName;
    }
    
    public void setClientDisplayName(String clientDisplayName) {
    	this.clientDisplayName = clientDisplayName;
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



}
