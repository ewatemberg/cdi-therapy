package frlp.utn.edu.ar.service;

import frlp.utn.edu.ar.domain.FormaVerbal;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FormaVerbal}.
 */
public interface FormaVerbalService {

    /**
     * Save a formaVerbal.
     *
     * @param formaVerbal the entity to save.
     * @return the persisted entity.
     */
    FormaVerbal save(FormaVerbal formaVerbal);

    /**
     * Get all the formaVerbals.
     *
     * @return the list of entities.
     */
    List<FormaVerbal> findAll();


    /**
     * Get the "id" formaVerbal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormaVerbal> findOne(Long id);

    /**
     * Delete the "id" formaVerbal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
