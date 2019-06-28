package fr.tycabris.web.rest;

import fr.tycabris.service.ParcService;
import fr.tycabris.web.rest.errors.BadRequestAlertException;
import fr.tycabris.service.dto.ParcDTO;
import fr.tycabris.service.dto.ParcCriteria;
import fr.tycabris.service.ParcQueryService;

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
 * REST controller for managing {@link fr.tycabris.domain.Parc}.
 */
@RestController
@RequestMapping("/api")
public class ParcResource {

    private final Logger log = LoggerFactory.getLogger(ParcResource.class);

    private static final String ENTITY_NAME = "parc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParcService parcService;

    private final ParcQueryService parcQueryService;

    public ParcResource(ParcService parcService, ParcQueryService parcQueryService) {
        this.parcService = parcService;
        this.parcQueryService = parcQueryService;
    }

    /**
     * {@code POST  /parcs} : Create a new parc.
     *
     * @param parcDTO the parcDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parcDTO, or with status {@code 400 (Bad Request)} if the parc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parcs")
    public ResponseEntity<ParcDTO> createParc(@Valid @RequestBody ParcDTO parcDTO) throws URISyntaxException {
        log.debug("REST request to save Parc : {}", parcDTO);
        if (parcDTO.getId() != null) {
            throw new BadRequestAlertException("A new parc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParcDTO result = parcService.save(parcDTO);
        return ResponseEntity.created(new URI("/api/parcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parcs} : Updates an existing parc.
     *
     * @param parcDTO the parcDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parcDTO,
     * or with status {@code 400 (Bad Request)} if the parcDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parcDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parcs")
    public ResponseEntity<ParcDTO> updateParc(@Valid @RequestBody ParcDTO parcDTO) throws URISyntaxException {
        log.debug("REST request to update Parc : {}", parcDTO);
        if (parcDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParcDTO result = parcService.save(parcDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parcDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parcs} : get all the parcs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parcs in body.
     */
    @GetMapping("/parcs")
    public ResponseEntity<List<ParcDTO>> getAllParcs(ParcCriteria criteria) {
        log.debug("REST request to get Parcs by criteria: {}", criteria);
        List<ParcDTO> entityList = parcQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /parcs/count} : count all the parcs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/parcs/count")
    public ResponseEntity<Long> countParcs(ParcCriteria criteria) {
        log.debug("REST request to count Parcs by criteria: {}", criteria);
        return ResponseEntity.ok().body(parcQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /parcs/:id} : get the "id" parc.
     *
     * @param id the id of the parcDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parcDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parcs/{id}")
    public ResponseEntity<ParcDTO> getParc(@PathVariable Long id) {
        log.debug("REST request to get Parc : {}", id);
        Optional<ParcDTO> parcDTO = parcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parcDTO);
    }

    /**
     * {@code DELETE  /parcs/:id} : delete the "id" parc.
     *
     * @param id the id of the parcDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parcs/{id}")
    public ResponseEntity<Void> deleteParc(@PathVariable Long id) {
        log.debug("REST request to delete Parc : {}", id);
        parcService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
