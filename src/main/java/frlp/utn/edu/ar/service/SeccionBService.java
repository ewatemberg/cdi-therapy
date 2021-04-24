package frlp.utn.edu.ar.service;

import frlp.utn.edu.ar.domain.SeccionB;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SeccionB}.
 */
public interface SeccionBService {
    /**
     * Save a seccionB.
     *
     * @param seccionB the entity to save.
     * @return the persisted entity.
     */
    SeccionB save(SeccionB seccionB);

    /**
     * Partially updates a seccionB.
     *
     * @param seccionB the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SeccionB> partialUpdate(SeccionB seccionB);

    /**
     * Get all the seccionBS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SeccionB> findAll(Pageable pageable);

    /**
     * Get the "id" seccionB.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SeccionB> findOne(Long id);

    /**
     * Delete the "id" seccionB.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
