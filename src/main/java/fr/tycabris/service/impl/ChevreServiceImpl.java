package fr.tycabris.service.impl;

import fr.tycabris.service.ChevreService;
import fr.tycabris.domain.Chevre;
import fr.tycabris.repository.ChevreRepository;
import fr.tycabris.service.dto.ChevreDTO;
import fr.tycabris.service.mapper.ChevreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Chevre}.
 */
@Service
@Transactional
public class ChevreServiceImpl implements ChevreService {

    private final Logger log = LoggerFactory.getLogger(ChevreServiceImpl.class);

    private final ChevreRepository chevreRepository;

    private final ChevreMapper chevreMapper;

    public ChevreServiceImpl(ChevreRepository chevreRepository, ChevreMapper chevreMapper) {
        this.chevreRepository = chevreRepository;
        this.chevreMapper = chevreMapper;
    }

    /**
     * Save a chevre.
     *
     * @param chevreDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ChevreDTO save(ChevreDTO chevreDTO) {
        log.debug("Request to save Chevre : {}", chevreDTO);
        Chevre chevre = chevreMapper.toEntity(chevreDTO);
        chevre = chevreRepository.save(chevre);
        return chevreMapper.toDto(chevre);
    }

    /**
     * Get all the chevres.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChevreDTO> findAll() {
        log.debug("Request to get all Chevres");
        return chevreRepository.findAll().stream()
            .map(chevreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }





 
    /**
     * Get one chevre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChevreDTO> findOne(Long id) {
        log.debug("Request to get Chevre : {}", id);
        return chevreRepository.findById(id)
            .map(chevreMapper::toDto);
    }

    /**
     * Delete the chevre by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chevre : {}", id);
        chevreRepository.deleteById(id);
    }
}
