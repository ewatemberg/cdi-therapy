package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.domain.UsoLenguaje;
import frlp.utn.edu.ar.repository.UsoLenguajeRepository;
import frlp.utn.edu.ar.service.UsoLenguajeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UsoLenguaje}.
 */
@Service
@Transactional
public class UsoLenguajeServiceImpl implements UsoLenguajeService {

    private final Logger log = LoggerFactory.getLogger(UsoLenguajeServiceImpl.class);

    private final UsoLenguajeRepository usoLenguajeRepository;

    public UsoLenguajeServiceImpl(UsoLenguajeRepository usoLenguajeRepository) {
        this.usoLenguajeRepository = usoLenguajeRepository;
    }

    @Override
    public UsoLenguaje save(UsoLenguaje usoLenguaje) {
        log.debug("Request to save UsoLenguaje : {}", usoLenguaje);
        return usoLenguajeRepository.save(usoLenguaje);
    }

    @Override
    public Optional<UsoLenguaje> partialUpdate(UsoLenguaje usoLenguaje) {
        log.debug("Request to partially update UsoLenguaje : {}", usoLenguaje);

        return usoLenguajeRepository
            .findById(usoLenguaje.getId())
            .map(
                existingUsoLenguaje -> {
                    if (usoLenguaje.getPregunta() != null) {
                        existingUsoLenguaje.setPregunta(usoLenguaje.getPregunta());
                    }

                    return existingUsoLenguaje;
                }
            )
            .map(usoLenguajeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsoLenguaje> findAll() {
        log.debug("Request to get all UsoLenguajes");
        return usoLenguajeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsoLenguaje> findOne(Long id) {
        log.debug("Request to get UsoLenguaje : {}", id);
        return usoLenguajeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UsoLenguaje : {}", id);
        usoLenguajeRepository.deleteById(id);
    }
}
