package fr.tycabris.service.impl;

import fr.tycabris.service.ParcService;
import fr.tycabris.domain.Parc;
import fr.tycabris.repository.ParcRepository;
import fr.tycabris.service.dto.ParcDTO;
import fr.tycabris.service.mapper.ParcMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Parc}.
 */
@Service
@Transactional
public class ParcServiceImpl implements ParcService {

    private final Logger log = LoggerFactory.getLogger(ParcServiceImpl.class);

    private final ParcRepository parcRepository;

    private final ParcMapper parcMapper;

    public ParcServiceImpl(ParcRepository parcRepository, ParcMapper parcMapper) {
        this.parcRepository = parcRepository;
        this.parcMapper = parcMapper;
    }

    /**
     * Save a parc.
     *
     * @param parcDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ParcDTO save(ParcDTO parcDTO) {
        log.debug("Request to save Parc : {}", parcDTO);
        Parc parc = parcMapper.toEntity(parcDTO);
        parc = parcRepository.save(parc);
        return parcMapper.toDto(parc);
    }

    /**
     * Get all the parcs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParcDTO> findAll() {
        log.debug("Request to get all Parcs");
        return parcRepository.findAll().stream()
            .map(parcMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one parc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParcDTO> findOne(Long id) {
        log.debug("Request to get Parc : {}", id);
        return parcRepository.findById(id)
            .map(parcMapper::toDto);
    }

    /**
     * Delete the parc by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parc : {}", id);
        parcRepository.deleteById(id);
    }
}
