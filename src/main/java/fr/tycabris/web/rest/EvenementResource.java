package fr.tycabris.web.rest;

import fr.tycabris.service.EvenementService;
import fr.tycabris.web.rest.errors.BadRequestAlertException;
import fr.tycabris.service.dto.EvenementDTO;
import fr.tycabris.service.dto.EvenementCriteria;
import fr.tycabris.service.EvenementQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.tycabris.domain.Evenement}.
 */
@RestController
@RequestMapping("/api")
public class EvenementResource {

    private final Logger log = LoggerFactory.getLogger(EvenementResource.class);

    private static final String ENTITY_NAME = "evenement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvenementService evenementService;

    private final EvenementQueryService evenementQueryService;

    public EvenementResource(EvenementService evenementService, EvenementQueryService evenementQueryService) {
        this.evenementService = evenementService;
        this.evenementQueryService = evenementQueryService;
    }

    /**
     * {@code POST  /evenements} : Create a new evenement.
     *
     * @param evenementDTO the evenementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evenementDTO, or with status {@code 400 (Bad Request)} if the evenement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evenements")
    public ResponseEntity<EvenementDTO> createEvenement(@Valid @RequestBody EvenementDTO evenementDTO) throws URISyntaxException {
        log.debug("REST request to save Evenement : {}", evenementDTO);
        if (evenementDTO.getId() != null) {
            throw new BadRequestAlertException("A new evenement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvenementDTO result = evenementService.save(evenementDTO);
        return ResponseEntity.created(new URI("/api/evenements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evenements} : Updates an existing evenement.
     *
     * @param evenementDTO the evenementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evenementDTO,
     * or with status {@code 400 (Bad Request)} if the evenementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evenementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evenements")
    public ResponseEntity<EvenementDTO> updateEvenement(@Valid @RequestBody EvenementDTO evenementDTO) throws URISyntaxException {
        log.debug("REST request to update Evenement : {}", evenementDTO);
        if (evenementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvenementDTO result = evenementService.save(evenementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evenementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evenements} : get all the evenements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evenements in body.
     */
    @GetMapping("/evenements")
    public ResponseEntity<List<EvenementDTO>> getAllEvenements(EvenementCriteria criteria) {
        log.debug("REST request to get Evenements by criteria: {}", criteria);
        List<EvenementDTO> entityList = evenementQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /evenements/count} : count all the evenements.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/evenements/count")
    public ResponseEntity<Long> countEvenements(EvenementCriteria criteria) {
        log.debug("REST request to count Evenements by criteria: {}", criteria);
        return ResponseEntity.ok().body(evenementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /evenements/:id} : get the "id" evenement.
     *
     * @param id the id of the evenementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evenementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evenements/{id}")
    public ResponseEntity<EvenementDTO> getEvenement(@PathVariable Long id) {
        log.debug("REST request to get Evenement : {}", id);
        Optional<EvenementDTO> evenementDTO = evenementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evenementDTO);
    }

    /**
     * {@code DELETE  /evenements/:id} : delete the "id" evenement.
     *
     * @param id the id of the evenementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evenements/{id}")
    public ResponseEntity<Void> deleteEvenement(@PathVariable Long id) {
        log.debug("REST request to delete Evenement : {}", id);
        evenementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
