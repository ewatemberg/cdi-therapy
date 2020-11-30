package frlp.utn.edu.ar.repository;

import frlp.utn.edu.ar.domain.SeccionB;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SeccionB entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeccionBRepository extends JpaRepository<SeccionB, Long> {
}
