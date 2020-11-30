package frlp.utn.edu.ar.service;

import frlp.utn.edu.ar.domain.UsoLenguaje;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link UsoLenguaje}.
 */
public interface UsoLenguajeService {

    /**
     * Save a usoLenguaje.
     *
     * @param usoLenguaje the entity to save.
     * @return the persisted entity.
     */
    UsoLenguaje save(UsoLenguaje usoLenguaje);

    /**
     * Get all the usoLenguajes.
     *
     * @return the list of entities.
     */
    List<UsoLenguaje> findAll();


    /**
     * Get the "id" usoLenguaje.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsoLenguaje> findOne(Long id);

    /**
     * Delete the "id" usoLenguaje.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
