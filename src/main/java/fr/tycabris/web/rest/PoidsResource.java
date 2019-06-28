package fr.tycabris.web.rest;

import fr.tycabris.service.PoidsService;
import fr.tycabris.web.rest.errors.BadRequestAlertException;
import fr.tycabris.service.dto.PoidsDTO;
import fr.tycabris.service.dto.PoidsCriteria;
import fr.tycabris.service.PoidsQueryService;

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
 * REST controller for managing {@link fr.tycabris.domain.Poids}.
 */
@RestController
@RequestMapping("/api")
public class PoidsResource {

    private final Logger log = LoggerFactory.getLogger(PoidsResource.class);

    private static final String ENTITY_NAME = "poids";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoidsService poidsService;

    private final PoidsQueryService poidsQueryService;

    public PoidsResource(PoidsService poidsService, PoidsQueryService poidsQueryService) {
        this.poidsService = poidsService;
        this.poidsQueryService = poidsQueryService;
    }

    /**
     * {@code POST  /poids} : Create a new poids.
     *
     * @param poidsDTO the poidsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poidsDTO, or with status {@code 400 (Bad Request)} if the poids has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/poids")
    public ResponseEntity<PoidsDTO> createPoids(@Valid @RequestBody PoidsDTO poidsDTO) throws URISyntaxException {
        log.debug("REST request to save Poids : {}", poidsDTO);
        if (poidsDTO.getId() != null) {
            throw new BadRequestAlertException("A new poids cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoidsDTO result = poidsService.save(poidsDTO);
        return ResponseEntity.created(new URI("/api/poids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /poids} : Updates an existing poids.
     *
     * @param poidsDTO the poidsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poidsDTO,
     * or with status {@code 400 (Bad Request)} if the poidsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poidsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/poids")
    public ResponseEntity<PoidsDTO> updatePoids(@Valid @RequestBody PoidsDTO poidsDTO) throws URISyntaxException {
        log.debug("REST request to update Poids : {}", poidsDTO);
        if (poidsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PoidsDTO result = poidsService.save(poidsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, poidsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /poids} : get all the poids.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poids in body.
     */
    @GetMapping("/poids")
    public ResponseEntity<List<PoidsDTO>> getAllPoids(PoidsCriteria criteria) {
        log.debug("REST request to get Poids by criteria: {}", criteria);
        List<PoidsDTO> entityList = poidsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /poids/count} : count all the poids.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/poids/count")
    public ResponseEntity<Long> countPoids(PoidsCriteria criteria) {
        log.debug("REST request to count Poids by criteria: {}", criteria);
        return ResponseEntity.ok().body(poidsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /poids/:id} : get the "id" poids.
     *
     * @param id the id of the poidsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poidsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/poids/{id}")
    public ResponseEntity<PoidsDTO> getPoids(@PathVariable Long id) {
        log.debug("REST request to get Poids : {}", id);
        Optional<PoidsDTO> poidsDTO = poidsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(poidsDTO);
    }

    /**
     * {@code DELETE  /poids/:id} : delete the "id" poids.
     *
     * @param id the id of the poidsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/poids/{id}")
    public ResponseEntity<Void> deletePoids(@PathVariable Long id) {
        log.debug("REST request to delete Poids : {}", id);
        poidsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
