package fr.tycabris.web.rest;

import fr.tycabris.service.TailleService;
import fr.tycabris.web.rest.errors.BadRequestAlertException;
import fr.tycabris.service.dto.TailleDTO;
import fr.tycabris.service.dto.TailleCriteria;
import fr.tycabris.service.TailleQueryService;

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
 * REST controller for managing {@link fr.tycabris.domain.Taille}.
 */
@RestController
@RequestMapping("/api")
public class TailleResource {

    private final Logger log = LoggerFactory.getLogger(TailleResource.class);

    private static final String ENTITY_NAME = "taille";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TailleService tailleService;

    private final TailleQueryService tailleQueryService;

    public TailleResource(TailleService tailleService, TailleQueryService tailleQueryService) {
        this.tailleService = tailleService;
        this.tailleQueryService = tailleQueryService;
    }

    /**
     * {@code POST  /tailles} : Create a new taille.
     *
     * @param tailleDTO the tailleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tailleDTO, or with status {@code 400 (Bad Request)} if the taille has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tailles")
    public ResponseEntity<TailleDTO> createTaille(@Valid @RequestBody TailleDTO tailleDTO) throws URISyntaxException {
        log.debug("REST request to save Taille : {}", tailleDTO);
        if (tailleDTO.getId() != null) {
            throw new BadRequestAlertException("A new taille cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TailleDTO result = tailleService.save(tailleDTO);
        return ResponseEntity.created(new URI("/api/tailles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tailles} : Updates an existing taille.
     *
     * @param tailleDTO the tailleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tailleDTO,
     * or with status {@code 400 (Bad Request)} if the tailleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tailleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tailles")
    public ResponseEntity<TailleDTO> updateTaille(@Valid @RequestBody TailleDTO tailleDTO) throws URISyntaxException {
        log.debug("REST request to update Taille : {}", tailleDTO);
        if (tailleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TailleDTO result = tailleService.save(tailleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tailleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tailles} : get all the tailles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tailles in body.
     */
    @GetMapping("/tailles")
    public ResponseEntity<List<TailleDTO>> getAllTailles(TailleCriteria criteria) {
        log.debug("REST request to get Tailles by criteria: {}", criteria);
        List<TailleDTO> entityList = tailleQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /tailles/count} : count all the tailles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tailles/count")
    public ResponseEntity<Long> countTailles(TailleCriteria criteria) {
        log.debug("REST request to count Tailles by criteria: {}", criteria);
        return ResponseEntity.ok().body(tailleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tailles/:id} : get the "id" taille.
     *
     * @param id the id of the tailleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tailleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tailles/{id}")
    public ResponseEntity<TailleDTO> getTaille(@PathVariable Long id) {
        log.debug("REST request to get Taille : {}", id);
        Optional<TailleDTO> tailleDTO = tailleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tailleDTO);
    }

    /**
     * {@code DELETE  /tailles/:id} : delete the "id" taille.
     *
     * @param id the id of the tailleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tailles/{id}")
    public ResponseEntity<Void> deleteTaille(@PathVariable Long id) {
        log.debug("REST request to delete Taille : {}", id);
        tailleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
