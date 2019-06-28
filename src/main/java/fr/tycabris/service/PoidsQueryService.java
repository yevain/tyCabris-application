package fr.tycabris.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import fr.tycabris.domain.Poids;
import fr.tycabris.domain.*; // for static metamodels
import fr.tycabris.repository.PoidsRepository;
import fr.tycabris.service.dto.PoidsCriteria;
import fr.tycabris.service.dto.PoidsDTO;
import fr.tycabris.service.mapper.PoidsMapper;

/**
 * Service for executing complex queries for {@link Poids} entities in the database.
 * The main input is a {@link PoidsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PoidsDTO} or a {@link Page} of {@link PoidsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PoidsQueryService extends QueryService<Poids> {

    private final Logger log = LoggerFactory.getLogger(PoidsQueryService.class);

    private final PoidsRepository poidsRepository;

    private final PoidsMapper poidsMapper;

    public PoidsQueryService(PoidsRepository poidsRepository, PoidsMapper poidsMapper) {
        this.poidsRepository = poidsRepository;
        this.poidsMapper = poidsMapper;
    }

    /**
     * Return a {@link List} of {@link PoidsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PoidsDTO> findByCriteria(PoidsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Poids> specification = createSpecification(criteria);
        return poidsMapper.toDto(poidsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PoidsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PoidsDTO> findByCriteria(PoidsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Poids> specification = createSpecification(criteria);
        return poidsRepository.findAll(specification, page)
            .map(poidsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PoidsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Poids> specification = createSpecification(criteria);
        return poidsRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Poids> createSpecification(PoidsCriteria criteria) {
        Specification<Poids> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Poids_.id));
            }
            if (criteria.getValeur() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeur(), Poids_.valeur));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Poids_.date));
            }
            if (criteria.getChevreId() != null) {
                specification = specification.and(buildSpecification(criteria.getChevreId(),
                    root -> root.join(Poids_.chevre, JoinType.LEFT).get(Chevre_.id)));
            }
        }
        return specification;
    }
}
