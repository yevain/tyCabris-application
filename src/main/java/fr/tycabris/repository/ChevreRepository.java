package fr.tycabris.repository;

import fr.tycabris.domain.Chevre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Chevre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChevreRepository extends JpaRepository<Chevre, Long>, JpaSpecificationExecutor<Chevre> {

}
