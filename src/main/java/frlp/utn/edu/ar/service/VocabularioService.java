package frlp.utn.edu.ar.service;

import frlp.utn.edu.ar.domain.Vocabulario;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Vocabulario}.
 */
public interface VocabularioService {
    /**
     * Save a vocabulario.
     *
     * @param vocabulario the entity to save.
     * @return the persisted entity.
     */
    Vocabulario save(Vocabulario vocabulario);

    /**
     * Partially updates a vocabulario.
     *
     * @param vocabulario the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Vocabulario> partialUpdate(Vocabulario vocabulario);

    /**
     * Get all the vocabularios.
     *
     * @return the list of entities.
     */
    List<Vocabulario> findAll();

    /**
     * Get the "id" vocabulario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Vocabulario> findOne(Long id);

    /**
     * Delete the "id" vocabulario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
