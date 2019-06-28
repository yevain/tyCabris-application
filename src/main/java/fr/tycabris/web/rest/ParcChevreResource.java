package fr.tycabris.web.rest;

import fr.tycabris.service.ParcChevreService;
import fr.tycabris.web.rest.errors.BadRequestAlertException;
import fr.tycabris.service.dto.ParcChevreDTO;
import fr.tycabris.service.dto.ParcChevreCriteria;
import fr.tycabris.service.ParcChevreQueryService;

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
 * REST controller for managing {@link fr.tycabris.domain.ParcChevre}.
 */
@RestController
@RequestMapping("/api")
public class ParcChevreResource {

    private final Logger log = LoggerFactory.getLogger(ParcChevreResource.class);

    private static final String ENTITY_NAME = "parcChevre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParcChevreService parcChevreService;

    private final ParcChevreQueryService parcChevreQueryService;

    public ParcChevreResource(ParcChevreService parcChevreService, ParcChevreQueryService parcChevreQueryService) {
        this.parcChevreService = parcChevreService;
        this.parcChevreQueryService = parcChevreQueryService;
    }

    /**
     * {@code POST  /parc-chevres} : Create a new parcChevre.
     *
     * @param parcChevreDTO the parcChevreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parcChevreDTO, or with status {@code 400 (Bad Request)} if the parcChevre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parc-chevres")
    public ResponseEntity<ParcChevreDTO> createParcChevre(@Valid @RequestBody ParcChevreDTO parcChevreDTO) throws URISyntaxException {
        log.debug("REST request to save ParcChevre : {}", parcChevreDTO);
        if (parcChevreDTO.getId() != null) {
            throw new BadRequestAlertException("A new parcChevre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParcChevreDTO result = parcChevreService.save(parcChevreDTO);
        return ResponseEntity.created(new URI("/api/parc-chevres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parc-chevres} : Updates an existing parcChevre.
     *
     * @param parcChevreDTO the parcChevreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parcChevreDTO,
     * or with status {@code 400 (Bad Request)} if the parcChevreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parcChevreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parc-chevres")
    public ResponseEntity<ParcChevreDTO> updateParcChevre(@Valid @RequestBody ParcChevreDTO parcChevreDTO) throws URISyntaxException {
        log.debug("REST request to update ParcChevre : {}", parcChevreDTO);
        if (parcChevreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParcChevreDTO result = parcChevreService.save(parcChevreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parcChevreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parc-chevres} : get all the parcChevres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parcChevres in body.
     */
    @GetMapping("/parc-chevres")
    public ResponseEntity<List<ParcChevreDTO>> getAllParcChevres(ParcChevreCriteria criteria) {
        log.debug("REST request to get ParcChevres by criteria: {}", criteria);
        List<ParcChevreDTO> entityList = parcChevreQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /parc-chevres/count} : count all the parcChevres.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/parc-chevres/count")
    public ResponseEntity<Long> countParcChevres(ParcChevreCriteria criteria) {
        log.debug("REST request to count ParcChevres by criteria: {}", criteria);
        return ResponseEntity.ok().body(parcChevreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /parc-chevres/:id} : get the "id" parcChevre.
     *
     * @param id the id of the parcChevreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parcChevreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parc-chevres/{id}")
    public ResponseEntity<ParcChevreDTO> getParcChevre(@PathVariable Long id) {
        log.debug("REST request to get ParcChevre : {}", id);
        Optional<ParcChevreDTO> parcChevreDTO = parcChevreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parcChevreDTO);
    }

    /**
     * {@code DELETE  /parc-chevres/:id} : delete the "id" parcChevre.
     *
     * @param id the id of the parcChevreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parc-chevres/{id}")
    public ResponseEntity<Void> deleteParcChevre(@PathVariable Long id) {
        log.debug("REST request to delete ParcChevre : {}", id);
        parcChevreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
