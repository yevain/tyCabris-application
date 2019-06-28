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

import fr.tycabris.domain.EvenementChevre;
import fr.tycabris.domain.*; // for static metamodels
import fr.tycabris.repository.EvenementChevreRepository;
import fr.tycabris.service.dto.EvenementChevreCriteria;
import fr.tycabris.service.dto.EvenementChevreDTO;
import fr.tycabris.service.mapper.EvenementChevreMapper;

/**
 * Service for executing complex queries for {@link EvenementChevre} entities in the database.
 * The main input is a {@link EvenementChevreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EvenementChevreDTO} or a {@link Page} of {@link EvenementChevreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EvenementChevreQueryService extends QueryService<EvenementChevre> {

    private final Logger log = LoggerFactory.getLogger(EvenementChevreQueryService.class);

    private final EvenementChevreRepository evenementChevreRepository;

    private final EvenementChevreMapper evenementChevreMapper;

    public EvenementChevreQueryService(EvenementChevreRepository evenementChevreRepository, EvenementChevreMapper evenementChevreMapper) {
        this.evenementChevreRepository = evenementChevreRepository;
        this.evenementChevreMapper = evenementChevreMapper;
    }

    /**
     * Return a {@link List} of {@link EvenementChevreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EvenementChevreDTO> findByCriteria(EvenementChevreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EvenementChevre> specification = createSpecification(criteria);
        return evenementChevreMapper.toDto(evenementChevreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EvenementChevreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EvenementChevreDTO> findByCriteria(EvenementChevreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EvenementChevre> specification = createSpecification(criteria);
        return evenementChevreRepository.findAll(specification, page)
            .map(evenementChevreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EvenementChevreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EvenementChevre> specification = createSpecification(criteria);
        return evenementChevreRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<EvenementChevre> createSpecification(EvenementChevreCriteria criteria) {
        Specification<EvenementChevre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EvenementChevre_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), EvenementChevre_.date));
            }
            if (criteria.getEvenementId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvenementId(),
                    root -> root.join(EvenementChevre_.evenement, JoinType.LEFT).get(Evenement_.id)));
            }
            if (criteria.getChevreId() != null) {
                specification = specification.and(buildSpecification(criteria.getChevreId(),
                    root -> root.join(EvenementChevre_.chevre, JoinType.LEFT).get(Chevre_.id)));
            }
        }
        return specification;
    }
}
