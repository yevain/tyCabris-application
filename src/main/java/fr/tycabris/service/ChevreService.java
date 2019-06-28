package fr.tycabris.service;

import fr.tycabris.service.dto.ChevreDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tycabris.domain.Chevre}.
 */
public interface ChevreService {

    /**
     * Save a chevre.
     *
     * @param chevreDTO the entity to save.
     * @return the persisted entity.
     */
    ChevreDTO save(ChevreDTO chevreDTO);

    /**
     * Get all the chevres.
     *
     * @return the list of entities.
     */
    List<ChevreDTO> findAll();
 

    /**
     * Get the "id" chevre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChevreDTO> findOne(Long id);

    /**
     * Delete the "id" chevre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
