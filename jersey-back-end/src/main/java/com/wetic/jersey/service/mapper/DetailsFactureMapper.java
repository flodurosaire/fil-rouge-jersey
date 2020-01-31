package com.wetic.jersey.service.mapper;

import com.wetic.jersey.domain.*;
import com.wetic.jersey.service.dto.DetailsFactureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetailsFacture} and its DTO {@link DetailsFactureDTO}.
 */
@Mapper(componentModel = "spring", uses = {FactureMapper.class, ProduitMapper.class})
public interface DetailsFactureMapper extends EntityMapper<DetailsFactureDTO, DetailsFacture> {

    @Mapping(source = "facture.id", target = "factureId")
    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "produit.libelle", target = "produitName")
    @Mapping(source = "produit.prix", target = "produitPrix")
    DetailsFactureDTO toDto(DetailsFacture detailsFacture);

    @Mapping(source = "factureId", target = "facture")
    @Mapping(source = "produitId", target = "produit")
    DetailsFacture toEntity(DetailsFactureDTO detailsFactureDTO);

    default DetailsFacture fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetailsFacture detailsFacture = new DetailsFacture();
        detailsFacture.setId(id);
        return detailsFacture;
    }
}
