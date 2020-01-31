package com.wetic.jersey.service.mapper;

import com.wetic.jersey.domain.*;
import com.wetic.jersey.service.dto.FactureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Facture} and its DTO {@link FactureDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface FactureMapper extends EntityMapper<FactureDTO, Facture> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.user.firstName", target = "clientName")
    FactureDTO toDto(Facture facture);

//    @Mapping(source = "client.user.firstName", target = "clientName")
//    FactureDTO toDto(Facture facture);

    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "detailsFacture", ignore = true)
    Facture toEntity(FactureDTO factureDTO);

    default Facture fromId(Long id) {
        if (id == null) {
            return null;
        }
        Facture facture = new Facture();
        facture.setId(id);
        return facture;
    }
}
