package frlp.utn.edu.ar.repository;

import frlp.utn.edu.ar.domain.SeccionC;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SeccionC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeccionCRepository extends JpaRepository<SeccionC, Long> {
}
