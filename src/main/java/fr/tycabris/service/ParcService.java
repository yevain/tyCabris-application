package fr.tycabris.service;

import fr.tycabris.service.dto.ParcDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tycabris.domain.Parc}.
 */
public interface ParcService {

    /**
     * Save a parc.
     *
     * @param parcDTO the entity to save.
     * @return the persisted entity.
     */
    ParcDTO save(ParcDTO parcDTO);

    /**
     * Get all the parcs.
     *
     * @return the list of entities.
     */
    List<ParcDTO> findAll();


    /**
     * Get the "id" parc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParcDTO> findOne(Long id);

    /**
     * Delete the "id" parc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
