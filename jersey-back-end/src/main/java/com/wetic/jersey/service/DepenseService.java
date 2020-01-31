package com.wetic.jersey.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wetic.jersey.service.dto.DepenseDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wetic.jersy.domain.Depense}.
 */
public interface DepenseService {

    /**
     * Save a depense.
     *
     * @param depenseDTO the entity to save.
     * @return the persisted entity.
     */
    DepenseDTO save(DepenseDTO depenseDTO);

    /**
     * Get all the depenses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DepenseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" depense.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepenseDTO> findOne(Long id);

    /**
     * Delete the "id" depense.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
