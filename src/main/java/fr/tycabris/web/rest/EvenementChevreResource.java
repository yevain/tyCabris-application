package fr.tycabris.web.rest;

import fr.tycabris.service.EvenementChevreService;
import fr.tycabris.web.rest.errors.BadRequestAlertException;
import fr.tycabris.service.dto.EvenementChevreDTO;
import fr.tycabris.service.dto.EvenementChevreCriteria;
import fr.tycabris.service.EvenementChevreQueryService;

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
 * REST controller for managing {@link fr.tycabris.domain.EvenementChevre}.
 */
@RestController
@RequestMapping("/api")
public class EvenementChevreResource {

    private final Logger log = LoggerFactory.getLogger(EvenementChevreResource.class);

    private static final String ENTITY_NAME = "evenementChevre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvenementChevreService evenementChevreService;

    private final EvenementChevreQueryService evenementChevreQueryService;

    public EvenementChevreResource(EvenementChevreService evenementChevreService, EvenementChevreQueryService evenementChevreQueryService) {
        this.evenementChevreService = evenementChevreService;
        this.evenementChevreQueryService = evenementChevreQueryService;
    }

    /**
     * {@code POST  /evenement-chevres} : Create a new evenementChevre.
     *
     * @param evenementChevreDTO the evenementChevreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evenementChevreDTO, or with status {@code 400 (Bad Request)} if the evenementChevre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evenement-chevres")
    public ResponseEntity<EvenementChevreDTO> createEvenementChevre(@Valid @RequestBody EvenementChevreDTO evenementChevreDTO) throws URISyntaxException {
        log.debug("REST request to save EvenementChevre : {}", evenementChevreDTO);
        if (evenementChevreDTO.getId() != null) {
            throw new BadRequestAlertException("A new evenementChevre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvenementChevreDTO result = evenementChevreService.save(evenementChevreDTO);
        return ResponseEntity.created(new URI("/api/evenement-chevres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evenement-chevres} : Updates an existing evenementChevre.
     *
     * @param evenementChevreDTO the evenementChevreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evenementChevreDTO,
     * or with status {@code 400 (Bad Request)} if the evenementChevreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evenementChevreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evenement-chevres")
    public ResponseEntity<EvenementChevreDTO> updateEvenementChevre(@Valid @RequestBody EvenementChevreDTO evenementChevreDTO) throws URISyntaxException {
        log.debug("REST request to update EvenementChevre : {}", evenementChevreDTO);
        if (evenementChevreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvenementChevreDTO result = evenementChevreService.save(evenementChevreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evenementChevreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evenement-chevres} : get all the evenementChevres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evenementChevres in body.
     */
    @GetMapping("/evenement-chevres")
    public ResponseEntity<List<EvenementChevreDTO>> getAllEvenementChevres(EvenementChevreCriteria criteria) {
        log.debug("REST request to get EvenementChevres by criteria: {}", criteria);
        List<EvenementChevreDTO> entityList = evenementChevreQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /evenement-chevres/count} : count all the evenementChevres.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/evenement-chevres/count")
    public ResponseEntity<Long> countEvenementChevres(EvenementChevreCriteria criteria) {
        log.debug("REST request to count EvenementChevres by criteria: {}", criteria);
        return ResponseEntity.ok().body(evenementChevreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /evenement-chevres/:id} : get the "id" evenementChevre.
     *
     * @param id the id of the evenementChevreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evenementChevreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evenement-chevres/{id}")
    public ResponseEntity<EvenementChevreDTO> getEvenementChevre(@PathVariable Long id) {
        log.debug("REST request to get EvenementChevre : {}", id);
        Optional<EvenementChevreDTO> evenementChevreDTO = evenementChevreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evenementChevreDTO);
    }

    /**
     * {@code DELETE  /evenement-chevres/:id} : delete the "id" evenementChevre.
     *
     * @param id the id of the evenementChevreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evenement-chevres/{id}")
    public ResponseEntity<Void> deleteEvenementChevre(@PathVariable Long id) {
        log.debug("REST request to delete EvenementChevre : {}", id);
        evenementChevreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
