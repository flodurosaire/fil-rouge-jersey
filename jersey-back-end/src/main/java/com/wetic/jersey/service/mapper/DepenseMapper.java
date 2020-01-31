package com.wetic.jersey.service.mapper;

import com.wetic.jersey.domain.*;
import com.wetic.jersey.service.dto.DepenseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Depense} and its DTO {@link DepenseDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeDepenseMapper.class})
public interface DepenseMapper extends EntityMapper<DepenseDTO, Depense> {

    @Mapping(source = "typeDepense.id", target = "typeDepenseId")
    @Mapping(source = "typeDepense.libelle", target = "typeDepenseName")
    DepenseDTO toDto(Depense depense);

    @Mapping(source = "typeDepenseId", target = "typeDepense")
    Depense toEntity(DepenseDTO depenseDTO);

    default Depense fromId(Long id) {
        if (id == null) {
            return null;
        }
        Depense depense = new Depense();
        depense.setId(id);
        return depense;
    }
}
