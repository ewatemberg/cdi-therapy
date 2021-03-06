package frlp.utn.edu.ar.service;

import frlp.utn.edu.ar.domain.SeccionD;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SeccionD}.
 */
public interface SeccionDService {
    /**
     * Save a seccionD.
     *
     * @param seccionD the entity to save.
     * @return the persisted entity.
     */
    SeccionD save(SeccionD seccionD);

    /**
     * Partially updates a seccionD.
     *
     * @param seccionD the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SeccionD> partialUpdate(SeccionD seccionD);

    /**
     * Get all the seccionDS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SeccionD> findAll(Pageable pageable);

    /**
     * Get the "id" seccionD.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeccionD> findOne(Long id);

    /**
     * Delete the "id" seccionD.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
