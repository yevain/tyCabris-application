package fr.tycabris.repository;

import fr.tycabris.domain.Taille;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Taille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TailleRepository extends JpaRepository<Taille, Long>, JpaSpecificationExecutor<Taille> {

}
