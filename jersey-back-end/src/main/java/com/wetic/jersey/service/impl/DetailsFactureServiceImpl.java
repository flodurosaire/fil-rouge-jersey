package com.wetic.jersey.service.impl;

import com.wetic.jersey.service.DetailsFactureService;
import com.wetic.jersey.service.dto.DetailsFactureDTO;
import com.wetic.jersey.service.mapper.DetailsFactureMapper;
import com.wetic.jersey.domain.DetailsFacture;
import com.wetic.jersey.repository.DetailsFactureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DetailsFacture}.
 */
@Service
@Transactional
public class DetailsFactureServiceImpl implements DetailsFactureService {

    private final Logger log = LoggerFactory.getLogger(DetailsFactureServiceImpl.class);

    private final DetailsFactureRepository detailsFactureRepository;

    private final DetailsFactureMapper detailsFactureMapper;

    public DetailsFactureServiceImpl(DetailsFactureRepository detailsFactureRepository, DetailsFactureMapper detailsFactureMapper) {
        this.detailsFactureRepository = detailsFactureRepository;
        this.detailsFactureMapper = detailsFactureMapper;
    }

    /**
     * Save a detailsFacture.
     *
     * @param detailsFactureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DetailsFactureDTO save(DetailsFactureDTO detailsFactureDTO) {
        log.debug("Request to save DetailsFacture : {}", detailsFactureDTO);        
        DetailsFacture detailsFacture = detailsFactureMapper.toEntity(detailsFactureDTO);
        detailsFacture = detailsFactureRepository.save(detailsFacture);
        return detailsFactureMapper.toDto(detailsFacture);
    }

    /**
     * Get all the detailsFactures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetailsFactureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetailsFactures");        
        return detailsFactureRepository.findAll(pageable)
            .map(detailsFactureMapper::toDto);
    }


    /**
     * Get one detailsFacture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetailsFactureDTO> findOne(Long id) {
        log.debug("Request to get DetailsFacture : {}", id);
        return detailsFactureRepository.findById(id)
            .map(detailsFactureMapper::toDto);
    }

    /**
     * Delete the detailsFacture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailsFacture : {}", id);
        detailsFactureRepository.deleteById(id);
    }
    
    public Page<DetailsFactureDTO> findDetailFactureByFactureId(Pageable pageable, long factureId) {
        log.debug("Request to get detailsFacture(s) by facture id");        
        return detailsFactureRepository.findDetailsFactureByFactureId(pageable, factureId)
            .map(detailsFactureMapper::toDto);
    }
    
}
