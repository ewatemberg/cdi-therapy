package frlp.utn.edu.ar.repository;

import frlp.utn.edu.ar.domain.FraseCompleja;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FraseCompleja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FraseComplejaRepository extends JpaRepository<FraseCompleja, Long> {
}
