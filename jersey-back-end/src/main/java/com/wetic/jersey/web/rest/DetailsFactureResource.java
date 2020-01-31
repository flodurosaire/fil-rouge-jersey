package com.wetic.jersey.web.rest;

import com.wetic.jersey.service.DetailsFactureService;
import com.wetic.jersey.web.rest.errors.BadRequestAlertException;
import com.wetic.jersey.service.dto.DetailsFactureDTO;

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
 * REST controller for managing {@link com.wetic.jersey.domain.DetailsFacture}.
 */
@RestController
@RequestMapping("/api")
public class DetailsFactureResource {

    private final Logger log = LoggerFactory.getLogger(DetailsFactureResource.class);

    private static final String ENTITY_NAME = "detailsFacture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailsFactureService detailsFactureService;

    public DetailsFactureResource(DetailsFactureService detailsFactureService) {
        this.detailsFactureService = detailsFactureService;
    }

    /**
     * {@code POST  /details-factures} : Create a new detailsFacture.
     *
     * @param detailsFactureDTO the detailsFactureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailsFactureDTO, or with status {@code 400 (Bad Request)} if the detailsFacture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/details-factures")
    public ResponseEntity<DetailsFactureDTO> createDetailsFacture(@Valid @RequestBody DetailsFactureDTO detailsFactureDTO) throws URISyntaxException {
        log.debug("REST request to save DetailsFacture : {}", detailsFactureDTO);
        if (detailsFactureDTO.getId() != null) {
            throw new BadRequestAlertException("A new detailsFacture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailsFactureDTO result = detailsFactureService.save(detailsFactureDTO);
        return ResponseEntity.created(new URI("/api/details-factures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /details-factures} : Updates an existing detailsFacture.
     *
     * @param detailsFactureDTO the detailsFactureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailsFactureDTO,
     * or with status {@code 400 (Bad Request)} if the detailsFactureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailsFactureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/details-factures")
    public ResponseEntity<DetailsFactureDTO> updateDetailsFacture(@Valid @RequestBody DetailsFactureDTO detailsFactureDTO) throws URISyntaxException {
        log.debug("REST request to update DetailsFacture : {}", detailsFactureDTO);
        if (detailsFactureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetailsFactureDTO result = detailsFactureService.save(detailsFactureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detailsFactureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /details-factures} : get all the detailsFactures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailsFactures in body.
     */
    @GetMapping("/details-factures")
    public ResponseEntity<List<DetailsFactureDTO>> getAllDetailsFactures(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of DetailsFactures");
        Page<DetailsFactureDTO> page = detailsFactureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /details-factures/:id} : get the "id" detailsFacture.
     *
     * @param id the id of the detailsFactureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailsFactureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/details-factures/{id}")
    public ResponseEntity<DetailsFactureDTO> getDetailsFacture(@PathVariable Long id) {
        log.debug("REST request to get DetailsFacture : {}", id);
        Optional<DetailsFactureDTO> detailsFactureDTO = detailsFactureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailsFactureDTO);
    }

    /**
     * {@code DELETE  /details-factures/:id} : delete the "id" detailsFacture.
     *
     * @param id the id of the detailsFactureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/details-factures/{id}")
    public ResponseEntity<Void> deleteDetailsFacture(@PathVariable Long id) {
        log.debug("REST request to delete DetailsFacture : {}", id);
        detailsFactureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/details-factures/facture/{id}")
    public ResponseEntity<List<DetailsFactureDTO>> getAllDetailsFacturesByFactureId(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @PathVariable Long id) {
        log.debug("REST request to get a page of DetailsFactures");
        Page<DetailsFactureDTO> page = detailsFactureService.findDetailFactureByFactureId(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
