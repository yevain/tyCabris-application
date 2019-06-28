package fr.tycabris.service;

import fr.tycabris.service.dto.ParcChevreDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tycabris.domain.ParcChevre}.
 */
public interface ParcChevreService {

    /**
     * Save a parcChevre.
     *
     * @param parcChevreDTO the entity to save.
     * @return the persisted entity.
     */
    ParcChevreDTO save(ParcChevreDTO parcChevreDTO);

    /**
     * Get all the parcChevres.
     *
     * @return the list of entities.
     */
    List<ParcChevreDTO> findAll();


    /**
     * Get the "id" parcChevre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParcChevreDTO> findOne(Long id);

    /**
     * Delete the "id" parcChevre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
