package com.wetic.jersey.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wetic.jersey.service.dto.TypeDepenseDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wetic.jersy.domain.TypeDepense}.
 */
public interface TypeDepenseService {

    /**
     * Save a typeDepense.
     *
     * @param typeDepenseDTO the entity to save.
     * @return the persisted entity.
     */
    TypeDepenseDTO save(TypeDepenseDTO typeDepenseDTO);

    /**
     * Get all the typeDepenses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeDepenseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" typeDepense.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeDepenseDTO> findOne(Long id);

    /**
     * Delete the "id" typeDepense.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
