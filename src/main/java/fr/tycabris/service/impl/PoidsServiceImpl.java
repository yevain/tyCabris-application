package fr.tycabris.service.impl;

import fr.tycabris.service.PoidsService;
import fr.tycabris.domain.Poids;
import fr.tycabris.repository.PoidsRepository;
import fr.tycabris.service.dto.PoidsDTO;
import fr.tycabris.service.mapper.PoidsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Poids}.
 */
@Service
@Transactional
public class PoidsServiceImpl implements PoidsService {

    private final Logger log = LoggerFactory.getLogger(PoidsServiceImpl.class);

    private final PoidsRepository poidsRepository;

    private final PoidsMapper poidsMapper;

    public PoidsServiceImpl(PoidsRepository poidsRepository, PoidsMapper poidsMapper) {
        this.poidsRepository = poidsRepository;
        this.poidsMapper = poidsMapper;
    }

    /**
     * Save a poids.
     *
     * @param poidsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PoidsDTO save(PoidsDTO poidsDTO) {
        log.debug("Request to save Poids : {}", poidsDTO);
        Poids poids = poidsMapper.toEntity(poidsDTO);
        poids = poidsRepository.save(poids);
        return poidsMapper.toDto(poids);
    }

    /**
     * Get all the poids.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PoidsDTO> findAll() {
        log.debug("Request to get all Poids");
        return poidsRepository.findAll().stream()
            .map(poidsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one poids by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PoidsDTO> findOne(Long id) {
        log.debug("Request to get Poids : {}", id);
        return poidsRepository.findById(id)
            .map(poidsMapper::toDto);
    }

    /**
     * Delete the poids by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Poids : {}", id);
        poidsRepository.deleteById(id);
    }
}
