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

import fr.tycabris.domain.Parc;
import fr.tycabris.domain.*; // for static metamodels
import fr.tycabris.repository.ParcRepository;
import fr.tycabris.service.dto.ParcCriteria;
import fr.tycabris.service.dto.ParcDTO;
import fr.tycabris.service.mapper.ParcMapper;

/**
 * Service for executing complex queries for {@link Parc} entities in the database.
 * The main input is a {@link ParcCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParcDTO} or a {@link Page} of {@link ParcDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParcQueryService extends QueryService<Parc> {

    private final Logger log = LoggerFactory.getLogger(ParcQueryService.class);

    private final ParcRepository parcRepository;

    private final ParcMapper parcMapper;

    public ParcQueryService(ParcRepository parcRepository, ParcMapper parcMapper) {
        this.parcRepository = parcRepository;
        this.parcMapper = parcMapper;
    }

    /**
     * Return a {@link List} of {@link ParcDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParcDTO> findByCriteria(ParcCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Parc> specification = createSpecification(criteria);
        return parcMapper.toDto(parcRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParcDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParcDTO> findByCriteria(ParcCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Parc> specification = createSpecification(criteria);
        return parcRepository.findAll(specification, page)
            .map(parcMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParcCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Parc> specification = createSpecification(criteria);
        return parcRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Parc> createSpecification(ParcCriteria criteria) {
        Specification<Parc> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Parc_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Parc_.nom));
            }
            if (criteria.getParcChevreId() != null) {
                specification = specification.and(buildSpecification(criteria.getParcChevreId(),
                    root -> root.join(Parc_.parcChevres, JoinType.LEFT).get(ParcChevre_.id)));
            }
        }
        return specification;
    }
}
