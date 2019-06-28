package fr.tycabris.repository;

import fr.tycabris.domain.ParcChevre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParcChevre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParcChevreRepository extends JpaRepository<ParcChevre, Long>, JpaSpecificationExecutor<ParcChevre> {

}
