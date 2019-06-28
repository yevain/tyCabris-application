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

import fr.tycabris.domain.Evenement;
import fr.tycabris.domain.*; // for static metamodels
import fr.tycabris.repository.EvenementRepository;
import fr.tycabris.service.dto.EvenementCriteria;
import fr.tycabris.service.dto.EvenementDTO;
import fr.tycabris.service.mapper.EvenementMapper;

/**
 * Service for executing complex queries for {@link Evenement} entities in the database.
 * The main input is a {@link EvenementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EvenementDTO} or a {@link Page} of {@link EvenementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EvenementQueryService extends QueryService<Evenement> {

    private final Logger log = LoggerFactory.getLogger(EvenementQueryService.class);

    private final EvenementRepository evenementRepository;

    private final EvenementMapper evenementMapper;

    public EvenementQueryService(EvenementRepository evenementRepository, EvenementMapper evenementMapper) {
        this.evenementRepository = evenementRepository;
        this.evenementMapper = evenementMapper;
    }

    /**
     * Return a {@link List} of {@link EvenementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EvenementDTO> findByCriteria(EvenementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Evenement> specification = createSpecification(criteria);
        return evenementMapper.toDto(evenementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EvenementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EvenementDTO> findByCriteria(EvenementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Evenement> specification = createSpecification(criteria);
        return evenementRepository.findAll(specification, page)
            .map(evenementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EvenementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Evenement> specification = createSpecification(criteria);
        return evenementRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Evenement> createSpecification(EvenementCriteria criteria) {
        Specification<Evenement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Evenement_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Evenement_.nom));
            }
            if (criteria.getOccurence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOccurence(), Evenement_.occurence));
            }
            if (criteria.getSuivantId() != null) {
                specification = specification.and(buildSpecification(criteria.getSuivantId(),
                    root -> root.join(Evenement_.suivants, JoinType.LEFT).get(Evenement_.id)));
            }
            if (criteria.getEvenementChevreId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvenementChevreId(),
                    root -> root.join(Evenement_.evenementChevres, JoinType.LEFT).get(EvenementChevre_.id)));
            }
            if (criteria.getEvenementId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvenementId(),
                    root -> root.join(Evenement_.evenement, JoinType.LEFT).get(Evenement_.id)));
            }
        }
        return specification;
    }
}
