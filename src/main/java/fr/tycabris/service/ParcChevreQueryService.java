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

import fr.tycabris.domain.ParcChevre;
import fr.tycabris.domain.*; // for static metamodels
import fr.tycabris.repository.ParcChevreRepository;
import fr.tycabris.service.dto.ParcChevreCriteria;
import fr.tycabris.service.dto.ParcChevreDTO;
import fr.tycabris.service.mapper.ParcChevreMapper;

/**
 * Service for executing complex queries for {@link ParcChevre} entities in the database.
 * The main input is a {@link ParcChevreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParcChevreDTO} or a {@link Page} of {@link ParcChevreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParcChevreQueryService extends QueryService<ParcChevre> {

    private final Logger log = LoggerFactory.getLogger(ParcChevreQueryService.class);

    private final ParcChevreRepository parcChevreRepository;

    private final ParcChevreMapper parcChevreMapper;

    public ParcChevreQueryService(ParcChevreRepository parcChevreRepository, ParcChevreMapper parcChevreMapper) {
        this.parcChevreRepository = parcChevreRepository;
        this.parcChevreMapper = parcChevreMapper;
    }

    /**
     * Return a {@link List} of {@link ParcChevreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParcChevreDTO> findByCriteria(ParcChevreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParcChevre> specification = createSpecification(criteria);
        return parcChevreMapper.toDto(parcChevreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParcChevreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParcChevreDTO> findByCriteria(ParcChevreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParcChevre> specification = createSpecification(criteria);
        return parcChevreRepository.findAll(specification, page)
            .map(parcChevreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParcChevreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParcChevre> specification = createSpecification(criteria);
        return parcChevreRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ParcChevre> createSpecification(ParcChevreCriteria criteria) {
        Specification<ParcChevre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParcChevre_.id));
            }
            if (criteria.getEntree() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntree(), ParcChevre_.entree));
            }
            if (criteria.getSortie() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortie(), ParcChevre_.sortie));
            }
            if (criteria.getParcId() != null) {
                specification = specification.and(buildSpecification(criteria.getParcId(),
                    root -> root.join(ParcChevre_.parc, JoinType.LEFT).get(Parc_.id)));
            }
            if (criteria.getChevreId() != null) {
                specification = specification.and(buildSpecification(criteria.getChevreId(),
                    root -> root.join(ParcChevre_.chevre, JoinType.LEFT).get(Chevre_.id)));
            }
        }
        return specification;
    }
}
