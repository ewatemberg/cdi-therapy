package frlp.utn.edu.ar.repository;

import frlp.utn.edu.ar.domain.Cuestionario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cuestionario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuestionarioRepository extends JpaRepository<Cuestionario, Long> {
}
