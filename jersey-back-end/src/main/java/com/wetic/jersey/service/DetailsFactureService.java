package com.wetic.jersey.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wetic.jersey.service.dto.DetailsFactureDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.wetic.jersy.domain.DetailsFacture}.
 */
public interface DetailsFactureService {

    /**
     * Save a detailsFacture.
     *
     * @param detailsFactureDTO the entity to save.
     * @return the persisted entity.
     */
    DetailsFactureDTO save(DetailsFactureDTO detailsFactureDTO);

    /**
     * Get all the detailsFactures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetailsFactureDTO> findAll(Pageable pageable);


    /**
     * Get the "id" detailsFacture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetailsFactureDTO> findOne(Long id);

    /**
     * Delete the "id" detailsFacture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    Page<DetailsFactureDTO> findDetailFactureByFactureId(Pageable pageable, long factureId);
}
