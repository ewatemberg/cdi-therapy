package frlp.utn.edu.ar.repository;

import frlp.utn.edu.ar.domain.UsoLenguaje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UsoLenguaje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsoLenguajeRepository extends JpaRepository<UsoLenguaje, Long> {}
