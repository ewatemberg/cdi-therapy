package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.domain.SeccionC;
import frlp.utn.edu.ar.repository.SeccionCRepository;
import frlp.utn.edu.ar.service.SeccionCService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SeccionC}.
 */
@Service
@Transactional
public class SeccionCServiceImpl implements SeccionCService {

    private final Logger log = LoggerFactory.getLogger(SeccionCServiceImpl.class);

    private final SeccionCRepository seccionCRepository;

    public SeccionCServiceImpl(SeccionCRepository seccionCRepository) {
        this.seccionCRepository = seccionCRepository;
    }

    @Override
    public SeccionC save(SeccionC seccionC) {
        log.debug("Request to save SeccionC : {}", seccionC);
        return seccionCRepository.save(seccionC);
    }

    @Override
    public Optional<SeccionC> partialUpdate(SeccionC seccionC) {
        log.debug("Request to partially update SeccionC : {}", seccionC);

        return seccionCRepository
            .findById(seccionC.getId())
            .map(
                existingSeccionC -> {
                    if (seccionC.getDescripcion() != null) {
                        existingSeccionC.setDescripcion(seccionC.getDescripcion());
                    }
                    if (seccionC.getValor() != null) {
                        existingSeccionC.setValor(seccionC.getValor());
                    }

                    return existingSeccionC;
                }
            )
            .map(seccionCRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SeccionC> findAll(Pageable pageable) {
        log.debug("Request to get all SeccionCS");
        return seccionCRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeccionC> findOne(Long id) {
        log.debug("Request to get SeccionC : {}", id);
        return seccionCRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeccionC : {}", id);
        seccionCRepository.deleteById(id);
    }
}
