package frlp.utn.edu.ar.service;

import frlp.utn.edu.ar.domain.SeccionA;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SeccionA}.
 */
public interface SeccionAService {
    /**
     * Save a seccionA.
     *
     * @param seccionA the entity to save.
     * @return the persisted entity.
     */
    SeccionA save(SeccionA seccionA);

    /**
     * Partially updates a seccionA.
     *
     * @param seccionA the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SeccionA> partialUpdate(SeccionA seccionA);

    /**
     * Get all the seccionAS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SeccionA> findAll(Pageable pageable);

    /**
     * Get the "id" seccionA.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeccionA> findOne(Long id);

    /**
     * Delete the "id" seccionA.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
