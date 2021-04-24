package frlp.utn.edu.ar.service;

import frlp.utn.edu.ar.domain.FraseCompleja;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FraseCompleja}.
 */
public interface FraseComplejaService {
    /**
     * Save a fraseCompleja.
     *
     * @param fraseCompleja the entity to save.
     * @return the persisted entity.
     */
    FraseCompleja save(FraseCompleja fraseCompleja);

    /**
     * Partially updates a fraseCompleja.
     *
     * @param fraseCompleja the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FraseCompleja> partialUpdate(FraseCompleja fraseCompleja);

    /**
     * Get all the fraseComplejas.
     *
     * @return the list of entities.
     */
    List<FraseCompleja> findAll();

    /**
     * Get the "id" fraseCompleja.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FraseCompleja> findOne(Long id);

    /**
     * Delete the "id" fraseCompleja.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
