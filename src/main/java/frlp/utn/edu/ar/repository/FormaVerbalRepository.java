package frlp.utn.edu.ar.repository;

import frlp.utn.edu.ar.domain.FormaVerbal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FormaVerbal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormaVerbalRepository extends JpaRepository<FormaVerbal, Long> {}
