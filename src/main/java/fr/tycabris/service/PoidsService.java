package fr.tycabris.service;

import fr.tycabris.service.dto.PoidsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tycabris.domain.Poids}.
 */
public interface PoidsService {

    /**
     * Save a poids.
     *
     * @param poidsDTO the entity to save.
     * @return the persisted entity.
     */
    PoidsDTO save(PoidsDTO poidsDTO);

    /**
     * Get all the poids.
     *
     * @return the list of entities.
     */
    List<PoidsDTO> findAll();


    /**
     * Get the "id" poids.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PoidsDTO> findOne(Long id);

    /**
     * Delete the "id" poids.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
