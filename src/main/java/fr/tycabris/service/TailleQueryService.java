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

import fr.tycabris.domain.Taille;
import fr.tycabris.domain.*; // for static metamodels
import fr.tycabris.repository.TailleRepository;
import fr.tycabris.service.dto.TailleCriteria;
import fr.tycabris.service.dto.TailleDTO;
import fr.tycabris.service.mapper.TailleMapper;

/**
 * Service for executing complex queries for {@link Taille} entities in the database.
 * The main input is a {@link TailleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TailleDTO} or a {@link Page} of {@link TailleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TailleQueryService extends QueryService<Taille> {

    private final Logger log = LoggerFactory.getLogger(TailleQueryService.class);

    private final TailleRepository tailleRepository;

    private final TailleMapper tailleMapper;

    public TailleQueryService(TailleRepository tailleRepository, TailleMapper tailleMapper) {
        this.tailleRepository = tailleRepository;
        this.tailleMapper = tailleMapper;
    }

    /**
     * Return a {@link List} of {@link TailleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TailleDTO> findByCriteria(TailleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Taille> specification = createSpecification(criteria);
        return tailleMapper.toDto(tailleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TailleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TailleDTO> findByCriteria(TailleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Taille> specification = createSpecification(criteria);
        return tailleRepository.findAll(specification, page)
            .map(tailleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TailleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Taille> specification = createSpecification(criteria);
        return tailleRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Taille> createSpecification(TailleCriteria criteria) {
        Specification<Taille> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Taille_.id));
            }
            if (criteria.getValeur() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeur(), Taille_.valeur));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Taille_.date));
            }
            if (criteria.getChevreId() != null) {
                specification = specification.and(buildSpecification(criteria.getChevreId(),
                    root -> root.join(Taille_.chevre, JoinType.LEFT).get(Chevre_.id)));
            }
        }
        return specification;
    }
}
