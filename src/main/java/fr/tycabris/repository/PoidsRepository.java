package fr.tycabris.repository;

import fr.tycabris.domain.Poids;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Poids entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoidsRepository extends JpaRepository<Poids, Long>, JpaSpecificationExecutor<Poids> {

}
