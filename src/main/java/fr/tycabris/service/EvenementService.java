package fr.tycabris.service;

import fr.tycabris.service.dto.EvenementDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tycabris.domain.Evenement}.
 */
public interface EvenementService {

    /**
     * Save a evenement.
     *
     * @param evenementDTO the entity to save.
     * @return the persisted entity.
     */
    EvenementDTO save(EvenementDTO evenementDTO);

    /**
     * Get all the evenements.
     *
     * @return the list of entities.
     */
    List<EvenementDTO> findAll();


    /**
     * Get the "id" evenement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvenementDTO> findOne(Long id);

    /**
     * Delete the "id" evenement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
