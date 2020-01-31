package com.wetic.jersey.service.impl;

import com.wetic.jersey.service.FactureService;
import com.wetic.jersey.service.dto.FactureDTO;
import com.wetic.jersey.service.mapper.FactureMapper;
import com.wetic.jersey.domain.Facture;
import com.wetic.jersey.repository.DetailsFactureRepository;
import com.wetic.jersey.repository.FactureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Facture}.
 */
@Service
@Transactional
public class FactureServiceImpl implements FactureService {

    private final Logger log = LoggerFactory.getLogger(FactureServiceImpl.class);

    private final FactureRepository factureRepository;
    private final DetailsFactureRepository detailsFactureRepository;

    private final FactureMapper factureMapper;

    public FactureServiceImpl(FactureRepository factureRepository, DetailsFactureRepository detailsFactureRepository, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
        this.detailsFactureRepository = detailsFactureRepository;
    }

    /**
     * Save a facture.
     *
     * @param factureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FactureDTO save(FactureDTO factureDTO) {
        log.debug("Request to save Facture : {}", factureDTO);
        Facture facture = factureMapper.toEntity(factureDTO);
        facture = factureRepository.save(facture);
        return factureMapper.toDto(facture);
    }

    /**
     * Get all the factures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FactureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Factures");
        return factureRepository.findAll(pageable)
            .map(factureMapper::toDto);
    }
    
    //AI
    @Transactional(readOnly = true)
    public Page<FactureDTO> findByClientId(Pageable pageable, Long id) {
        log.debug("Request to get by client id");        
        return factureRepository.findByClientId(pageable, id)
            .map(factureMapper::toDto);
    }
    

    /**
     * Get one facture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FactureDTO> findOne(Long id) {
        log.debug("Request to get Facture : {}", id);        
        return factureRepository.findById(id)
            .map(factureMapper::toDto);
    }

    /**
     * Delete the facture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Facture : {}", id);
        // deleting details facture
        detailsFactureRepository.deleteByFactureId(id);
        factureRepository.deleteById(id);
    }
}

