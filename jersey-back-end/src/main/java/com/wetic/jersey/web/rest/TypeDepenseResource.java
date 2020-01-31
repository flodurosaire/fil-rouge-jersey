package com.wetic.jersey.web.rest;

import com.wetic.jersey.service.TypeDepenseService;
import com.wetic.jersey.web.rest.errors.BadRequestAlertException;
import com.wetic.jersey.service.dto.TypeDepenseDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.wetic.jersey.domain.TypeDepense}.
 */
@RestController
@RequestMapping("/api")
public class TypeDepenseResource {

    private final Logger log = LoggerFactory.getLogger(TypeDepenseResource.class);

    private static final String ENTITY_NAME = "typeDepense";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeDepenseService typeDepenseService;

    public TypeDepenseResource(TypeDepenseService typeDepenseService) {
        this.typeDepenseService = typeDepenseService;
    }

    /**
     * {@code POST  /type-depenses} : Create a new typeDepense.
     *
     * @param typeDepenseDTO the typeDepenseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeDepenseDTO, or with status {@code 400 (Bad Request)} if the typeDepense has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-depenses")
    public ResponseEntity<TypeDepenseDTO> createTypeDepense(@Valid @RequestBody TypeDepenseDTO typeDepenseDTO) 
    		throws URISyntaxException {
        log.debug("REST request to save TypeDepense : {}", typeDepenseDTO);
        if (typeDepenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeDepense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDepenseDTO result = typeDepenseService.save(typeDepenseDTO);
        return ResponseEntity.created(new URI("/api/type-depenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-depenses} : Updates an existing typeDepense.
     *
     * @param typeDepenseDTO the typeDepenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeDepenseDTO,
     * or with status {@code 400 (Bad Request)} if the typeDepenseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeDepenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-depenses")
    public ResponseEntity<TypeDepenseDTO> updateTypeDepense(@Valid @RequestBody TypeDepenseDTO typeDepenseDTO) throws URISyntaxException {
        log.debug("REST request to update TypeDepense : {}", typeDepenseDTO);
        if (typeDepenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeDepenseDTO result = typeDepenseService.save(typeDepenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeDepenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-depenses} : get all the typeDepenses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeDepenses in body.
     */
    @GetMapping("/type-depenses")
    public ResponseEntity<List<TypeDepenseDTO>> getAllTypeDepenses(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of TypeDepenses");
        Page<TypeDepenseDTO> page = typeDepenseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-depenses/:id} : get the "id" typeDepense.
     *
     * @param id the id of the typeDepenseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeDepenseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-depenses/{id}")
    public ResponseEntity<TypeDepenseDTO> getTypeDepense(@PathVariable Long id) {
        log.debug("REST request to get TypeDepense : {}", id);
        Optional<TypeDepenseDTO> typeDepenseDTO = typeDepenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeDepenseDTO);
    }

    /**
     * {@code DELETE  /type-depenses/:id} : delete the "id" typeDepense.
     *
     * @param id the id of the typeDepenseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-depenses/{id}")
    public ResponseEntity<Void> deleteTypeDepense(@PathVariable Long id) {
        log.debug("REST request to delete TypeDepense : {}", id);
        typeDepenseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
