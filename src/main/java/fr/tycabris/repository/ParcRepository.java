package fr.tycabris.repository;

import fr.tycabris.domain.Parc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Parc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParcRepository extends JpaRepository<Parc, Long>, JpaSpecificationExecutor<Parc> {

}
