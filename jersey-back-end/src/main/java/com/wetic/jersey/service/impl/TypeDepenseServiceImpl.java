package com.wetic.jersey.service.impl;

import com.wetic.jersey.service.TypeDepenseService;
import com.wetic.jersey.service.dto.TypeDepenseDTO;
import com.wetic.jersey.service.mapper.TypeDepenseMapper;
import com.wetic.jersey.domain.TypeDepense;
import com.wetic.jersey.repository.TypeDepenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeDepense}.
 */
@Service
@Transactional
public class TypeDepenseServiceImpl implements TypeDepenseService {

    private final Logger log = LoggerFactory.getLogger(TypeDepenseServiceImpl.class);

    private final TypeDepenseRepository typeDepenseRepository;

    private final TypeDepenseMapper typeDepenseMapper;

    public TypeDepenseServiceImpl(TypeDepenseRepository typeDepenseRepository, TypeDepenseMapper typeDepenseMapper) {
        this.typeDepenseRepository = typeDepenseRepository;
        this.typeDepenseMapper = typeDepenseMapper;
    }

    /**
     * Save a typeDepense.
     *
     * @param typeDepenseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeDepenseDTO save(TypeDepenseDTO typeDepenseDTO) {
        log.debug("Request to save TypeDepense : {}", typeDepenseDTO);
        TypeDepense typeDepense = typeDepenseMapper.toEntity(typeDepenseDTO);
        typeDepense = typeDepenseRepository.save(typeDepense);
        return typeDepenseMapper.toDto(typeDepense);
    }

    /**
     * Get all the typeDepenses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeDepenseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeDepenses");
        return typeDepenseRepository.findAll(pageable)
            .map(typeDepenseMapper::toDto);
    }


    /**
     * Get one typeDepense by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeDepenseDTO> findOne(Long id) {
        log.debug("Request to get TypeDepense : {}", id);
        return typeDepenseRepository.findById(id)
            .map(typeDepenseMapper::toDto);
    }

    /**
     * Delete the typeDepense by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeDepense : {}", id);
        typeDepenseRepository.deleteById(id);
    }
}
