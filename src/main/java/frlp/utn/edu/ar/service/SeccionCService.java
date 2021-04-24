package frlp.utn.edu.ar.service;

import frlp.utn.edu.ar.domain.SeccionC;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SeccionC}.
 */
public interface SeccionCService {
    /**
     * Save a seccionC.
     *
     * @param seccionC the entity to save.
     * @return the persisted entity.
     */
    SeccionC save(SeccionC seccionC);

    /**
     * Partially updates a seccionC.
     *
     * @param seccionC the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SeccionC> partialUpdate(SeccionC seccionC);

    /**
     * Get all the seccionCS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SeccionC> findAll(Pageable pageable);

    /**
     * Get the "id" seccionC.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeccionC> findOne(Long id);

    /**
     * Delete the "id" seccionC.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
