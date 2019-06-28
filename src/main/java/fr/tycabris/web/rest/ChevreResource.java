package fr.tycabris.web.rest;

import fr.tycabris.service.ChevreService;
import fr.tycabris.web.rest.errors.BadRequestAlertException;
import fr.tycabris.service.dto.ChevreDTO;
import fr.tycabris.service.dto.ChevreCriteria;
import fr.tycabris.service.ChevreQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link fr.tycabris.domain.Chevre}.
 */
@RestController
@RequestMapping("/api")
public class ChevreResource {

    private final Logger log = LoggerFactory.getLogger(ChevreResource.class);

    private static final String ENTITY_NAME = "chevre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChevreService chevreService;

    private final ChevreQueryService chevreQueryService;

    public ChevreResource(ChevreService chevreService, ChevreQueryService chevreQueryService) {
        this.chevreService = chevreService;
        this.chevreQueryService = chevreQueryService;
    }

    /**
     * {@code POST  /chevres} : Create a new chevre.
     *
     * @param chevreDTO the chevreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chevreDTO, or with status {@code 400 (Bad Request)} if the chevre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chevres")
    public ResponseEntity<ChevreDTO> createChevre(@Valid @RequestBody ChevreDTO chevreDTO) throws URISyntaxException {
        log.debug("REST request to save Chevre : {}", chevreDTO);
        if (chevreDTO.getId() != null) {
            throw new BadRequestAlertException("A new chevre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChevreDTO result = chevreService.save(chevreDTO);
        return ResponseEntity.created(new URI("/api/chevres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chevres} : Updates an existing chevre.
     *
     * @param chevreDTO the chevreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chevreDTO,
     * or with status {@code 400 (Bad Request)} if the chevreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chevreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chevres")
    public ResponseEntity<ChevreDTO> updateChevre(@Valid @RequestBody ChevreDTO chevreDTO) throws URISyntaxException {
        log.debug("REST request to update Chevre : {}", chevreDTO);
        if (chevreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChevreDTO result = chevreService.save(chevreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chevreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chevres} : get all the chevres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chevres in body.
     */
    @GetMapping("/chevres")
    public ResponseEntity<List<ChevreDTO>> getAllChevres(ChevreCriteria criteria) {
        log.debug("REST request to get Chevres by criteria: {}", criteria);
        List<ChevreDTO> entityList = chevreQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /chevres/count} : count all the chevres.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/chevres/count")
    public ResponseEntity<Long> countChevres(ChevreCriteria criteria) {
        log.debug("REST request to count Chevres by criteria: {}", criteria);
        return ResponseEntity.ok().body(chevreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /chevres/:id} : get the "id" chevre.
     *
     * @param id the id of the chevreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chevreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chevres/{id}")
    public ResponseEntity<ChevreDTO> getChevre(@PathVariable Long id) {
        log.debug("REST request to get Chevre : {}", id);
        Optional<ChevreDTO> chevreDTO = chevreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chevreDTO);
    }

    /**
     * {@code DELETE  /chevres/:id} : delete the "id" chevre.
     *
     * @param id the id of the chevreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chevres/{id}")
    public ResponseEntity<Void> deleteChevre(@PathVariable Long id) {
        log.debug("REST request to delete Chevre : {}", id);
        chevreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
