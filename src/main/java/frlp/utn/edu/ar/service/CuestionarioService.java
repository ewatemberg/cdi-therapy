package frlp.utn.edu.ar.service;

import frlp.utn.edu.ar.domain.Cuestionario;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Cuestionario}.
 */
public interface CuestionarioService {
    /**
     * Save a cuestionario.
     *
     * @param cuestionario the entity to save.
     * @return the persisted entity.
     */
    Cuestionario save(Cuestionario cuestionario);

    /**
     * Partially updates a cuestionario.
     *
     * @param cuestionario the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Cuestionario> partialUpdate(Cuestionario cuestionario);

    /**
     * Get all the cuestionarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cuestionario> findAll(Pageable pageable);

    /**
     * Get the "id" cuestionario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cuestionario> findOne(Long id);

    /**
     * Delete the "id" cuestionario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
