package fr.tycabris.service;

import fr.tycabris.service.dto.TailleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tycabris.domain.Taille}.
 */
public interface TailleService {

    /**
     * Save a taille.
     *
     * @param tailleDTO the entity to save.
     * @return the persisted entity.
     */
    TailleDTO save(TailleDTO tailleDTO);

    /**
     * Get all the tailles.
     *
     * @return the list of entities.
     */
    List<TailleDTO> findAll();


    /**
     * Get the "id" taille.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TailleDTO> findOne(Long id);

    /**
     * Delete the "id" taille.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
