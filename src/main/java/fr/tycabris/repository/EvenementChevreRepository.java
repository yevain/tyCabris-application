package fr.tycabris.repository;

import fr.tycabris.domain.EvenementChevre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EvenementChevre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvenementChevreRepository extends JpaRepository<EvenementChevre, Long>, JpaSpecificationExecutor<EvenementChevre> {

}
