package fr.tycabris.service.impl;

import fr.tycabris.service.ParcChevreService;
import fr.tycabris.domain.ParcChevre;
import fr.tycabris.repository.ParcChevreRepository;
import fr.tycabris.service.dto.ParcChevreDTO;
import fr.tycabris.service.mapper.ParcChevreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ParcChevre}.
 */
@Service
@Transactional
public class ParcChevreServiceImpl implements ParcChevreService {

    private final Logger log = LoggerFactory.getLogger(ParcChevreServiceImpl.class);

    private final ParcChevreRepository parcChevreRepository;

    private final ParcChevreMapper parcChevreMapper;

    public ParcChevreServiceImpl(ParcChevreRepository parcChevreRepository, ParcChevreMapper parcChevreMapper) {
        this.parcChevreRepository = parcChevreRepository;
        this.parcChevreMapper = parcChevreMapper;
    }

    /**
     * Save a parcChevre.
     *
     * @param parcChevreDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ParcChevreDTO save(ParcChevreDTO parcChevreDTO) {
        log.debug("Request to save ParcChevre : {}", parcChevreDTO);
        ParcChevre parcChevre = parcChevreMapper.toEntity(parcChevreDTO);
        parcChevre = parcChevreRepository.save(parcChevre);
        return parcChevreMapper.toDto(parcChevre);
    }

    /**
     * Get all the parcChevres.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParcChevreDTO> findAll() {
        log.debug("Request to get all ParcChevres");
        return parcChevreRepository.findAll().stream()
            .map(parcChevreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one parcChevre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParcChevreDTO> findOne(Long id) {
        log.debug("Request to get ParcChevre : {}", id);
        return parcChevreRepository.findById(id)
            .map(parcChevreMapper::toDto);
    }

    /**
     * Delete the parcChevre by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParcChevre : {}", id);
        parcChevreRepository.deleteById(id);
    }
}
