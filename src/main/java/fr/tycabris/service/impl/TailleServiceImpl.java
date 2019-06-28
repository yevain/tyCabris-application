package fr.tycabris.service.impl;

import fr.tycabris.service.TailleService;
import fr.tycabris.domain.Taille;
import fr.tycabris.repository.TailleRepository;
import fr.tycabris.service.dto.TailleDTO;
import fr.tycabris.service.mapper.TailleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Taille}.
 */
@Service
@Transactional
public class TailleServiceImpl implements TailleService {

    private final Logger log = LoggerFactory.getLogger(TailleServiceImpl.class);

    private final TailleRepository tailleRepository;

    private final TailleMapper tailleMapper;

    public TailleServiceImpl(TailleRepository tailleRepository, TailleMapper tailleMapper) {
        this.tailleRepository = tailleRepository;
        this.tailleMapper = tailleMapper;
    }

    /**
     * Save a taille.
     *
     * @param tailleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TailleDTO save(TailleDTO tailleDTO) {
        log.debug("Request to save Taille : {}", tailleDTO);
        Taille taille = tailleMapper.toEntity(tailleDTO);
        taille = tailleRepository.save(taille);
        return tailleMapper.toDto(taille);
    }

    /**
     * Get all the tailles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TailleDTO> findAll() {
        log.debug("Request to get all Tailles");
        return tailleRepository.findAll().stream()
            .map(tailleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one taille by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TailleDTO> findOne(Long id) {
        log.debug("Request to get Taille : {}", id);
        return tailleRepository.findById(id)
            .map(tailleMapper::toDto);
    }

    /**
     * Delete the taille by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Taille : {}", id);
        tailleRepository.deleteById(id);
    }
}
