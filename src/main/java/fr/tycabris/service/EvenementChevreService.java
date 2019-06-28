package fr.tycabris.service;

import fr.tycabris.service.dto.EvenementChevreDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tycabris.domain.EvenementChevre}.
 */
public interface EvenementChevreService {

    /**
     * Save a evenementChevre.
     *
     * @param evenementChevreDTO the entity to save.
     * @return the persisted entity.
     */
    EvenementChevreDTO save(EvenementChevreDTO evenementChevreDTO);

    /**
     * Get all the evenementChevres.
     *
     * @return the list of entities.
     */
    List<EvenementChevreDTO> findAll();


    /**
     * Get the "id" evenementChevre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvenementChevreDTO> findOne(Long id);

    /**
     * Delete the "id" evenementChevre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
