package com.wetic.jersey.service.mapper;

import com.wetic.jersey.domain.*;
import com.wetic.jersey.service.dto.ProduitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {


    @Mapping(target = "detailsFacture", ignore = true)
    Produit toEntity(ProduitDTO produitDTO);

    default Produit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setId(id);
        return produit;
    }
}
