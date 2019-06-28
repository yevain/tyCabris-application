package fr.tycabris.service.impl;

import fr.tycabris.service.EvenementChevreService;
import fr.tycabris.domain.EvenementChevre;
import fr.tycabris.repository.EvenementChevreRepository;
import fr.tycabris.service.dto.EvenementChevreDTO;
import fr.tycabris.service.mapper.EvenementChevreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EvenementChevre}.
 */
@Service
@Transactional
public class EvenementChevreServiceImpl implements EvenementChevreService {

    private final Logger log = LoggerFactory.getLogger(EvenementChevreServiceImpl.class);

    private final EvenementChevreRepository evenementChevreRepository;

    private final EvenementChevreMapper evenementChevreMapper;

    public EvenementChevreServiceImpl(EvenementChevreRepository evenementChevreRepository, EvenementChevreMapper evenementChevreMapper) {
        this.evenementChevreRepository = evenementChevreRepository;
        this.evenementChevreMapper = evenementChevreMapper;
    }

    /**
     * Save a evenementChevre.
     *
     * @param evenementChevreDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EvenementChevreDTO save(EvenementChevreDTO evenementChevreDTO) {
        log.debug("Request to save EvenementChevre : {}", evenementChevreDTO);
        EvenementChevre evenementChevre = evenementChevreMapper.toEntity(evenementChevreDTO);
        evenementChevre = evenementChevreRepository.save(evenementChevre);
        return evenementChevreMapper.toDto(evenementChevre);
    }

    /**
     * Get all the evenementChevres.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EvenementChevreDTO> findAll() {
        log.debug("Request to get all EvenementChevres");
        return evenementChevreRepository.findAll().stream()
            .map(evenementChevreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one evenementChevre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EvenementChevreDTO> findOne(Long id) {
        log.debug("Request to get EvenementChevre : {}", id);
        return evenementChevreRepository.findById(id)
            .map(evenementChevreMapper::toDto);
    }

    /**
     * Delete the evenementChevre by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvenementChevre : {}", id);
        evenementChevreRepository.deleteById(id);
    }
}
