package frlp.utn.edu.ar.repository;

import frlp.utn.edu.ar.domain.Vocabulario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vocabulario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VocabularioRepository extends JpaRepository<Vocabulario, Long> {
}
