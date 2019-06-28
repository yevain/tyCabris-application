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

import fr.tycabris.domain.Chevre;
import fr.tycabris.domain.*; // for static metamodels
import fr.tycabris.repository.ChevreRepository;
import fr.tycabris.service.dto.ChevreCriteria;
import fr.tycabris.service.dto.ChevreDTO;
import fr.tycabris.service.mapper.ChevreMapper;

/**
 * Service for executing complex queries for {@link Chevre} entities in the database.
 * The main input is a {@link ChevreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChevreDTO} or a {@link Page} of {@link ChevreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChevreQueryService extends QueryService<Chevre> {

    private final Logger log = LoggerFactory.getLogger(ChevreQueryService.class);

    private final ChevreRepository chevreRepository;

    private final ChevreMapper chevreMapper;

    public ChevreQueryService(ChevreRepository chevreRepository, ChevreMapper chevreMapper) {
        this.chevreRepository = chevreRepository;
        this.chevreMapper = chevreMapper;
    }

    /**
     * Return a {@link List} of {@link ChevreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChevreDTO> findByCriteria(ChevreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Chevre> specification = createSpecification(criteria);
        return chevreMapper.toDto(chevreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChevreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChevreDTO> findByCriteria(ChevreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Chevre> specification = createSpecification(criteria);
        return chevreRepository.findAll(specification, page)
            .map(chevreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChevreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Chevre> specification = createSpecification(criteria);
        return chevreRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Chevre> createSpecification(ChevreCriteria criteria) {
        Specification<Chevre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Chevre_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Chevre_.nom));
            }
            if (criteria.getMatricule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatricule(), Chevre_.matricule));
            }
            if (criteria.getSurnom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurnom(), Chevre_.surnom));
            }
            if (criteria.getNaissance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNaissance(), Chevre_.naissance));
            }
            if (criteria.getPresent() != null) {
                specification = specification.and(buildSpecification(criteria.getPresent(), Chevre_.present));
            }
            if (criteria.getPereId() != null) {
                specification = specification.and(buildSpecification(criteria.getPereId(),
                    root -> root.join(Chevre_.pere, JoinType.LEFT).get(Chevre_.id)));
            }
            if (criteria.getMereId() != null) {
                specification = specification.and(buildSpecification(criteria.getMereId(),
                    root -> root.join(Chevre_.mere, JoinType.LEFT).get(Chevre_.id)));
            }
            if (criteria.getPoidsId() != null) {
                specification = specification.and(buildSpecification(criteria.getPoidsId(),
                    root -> root.join(Chevre_.poids, JoinType.LEFT).get(Poids_.id)));
            }
            if (criteria.getTailleId() != null) {
                specification = specification.and(buildSpecification(criteria.getTailleId(),
                    root -> root.join(Chevre_.tailles, JoinType.LEFT).get(Taille_.id)));
            }
            if (criteria.getParcChevreId() != null) {
                specification = specification.and(buildSpecification(criteria.getParcChevreId(),
                    root -> root.join(Chevre_.parcChevres, JoinType.LEFT).get(ParcChevre_.id)));
            }
            if (criteria.getEvenementChevreId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvenementChevreId(),
                    root -> root.join(Chevre_.evenementChevres, JoinType.LEFT).get(EvenementChevre_.id)));
            }
        }
        return specification;
    }
}
