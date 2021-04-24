package frlp.utn.edu.ar.repository;

import frlp.utn.edu.ar.domain.SeccionA;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SeccionA entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeccionARepository extends JpaRepository<SeccionA, Long> {}
