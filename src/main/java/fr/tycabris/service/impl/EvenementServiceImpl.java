package fr.tycabris.service.impl;

import fr.tycabris.service.EvenementService;
import fr.tycabris.domain.Evenement;
import fr.tycabris.repository.EvenementRepository;
import fr.tycabris.service.dto.EvenementDTO;
import fr.tycabris.service.mapper.EvenementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Evenement}.
 */
@Service
@Transactional
public class EvenementServiceImpl implements EvenementService {

    private final Logger log = LoggerFactory.getLogger(EvenementServiceImpl.class);

    private final EvenementRepository evenementRepository;

    private final EvenementMapper evenementMapper;

    public EvenementServiceImpl(EvenementRepository evenementRepository, EvenementMapper evenementMapper) {
        this.evenementRepository = evenementRepository;
        this.evenementMapper = evenementMapper;
    }

    /**
     * Save a evenement.
     *
     * @param evenementDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EvenementDTO save(EvenementDTO evenementDTO) {
        log.debug("Request to save Evenement : {}", evenementDTO);
        Evenement evenement = evenementMapper.toEntity(evenementDTO);
        evenement = evenementRepository.save(evenement);
        return evenementMapper.toDto(evenement);
    }

    /**
     * Get all the evenements.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EvenementDTO> findAll() {
        log.debug("Request to get all Evenements");
        return evenementRepository.findAll().stream()
            .map(evenementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one evenement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EvenementDTO> findOne(Long id) {
        log.debug("Request to get Evenement : {}", id);
        return evenementRepository.findById(id)
            .map(evenementMapper::toDto);
    }

    /**
     * Delete the evenement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evenement : {}", id);
        evenementRepository.deleteById(id);
    }
}
