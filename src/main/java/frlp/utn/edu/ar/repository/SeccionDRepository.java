package frlp.utn.edu.ar.repository;

import frlp.utn.edu.ar.domain.SeccionD;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SeccionD entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeccionDRepository extends JpaRepository<SeccionD, Long> {
}
