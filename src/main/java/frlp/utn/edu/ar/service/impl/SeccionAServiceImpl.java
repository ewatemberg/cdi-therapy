package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.domain.SeccionA;
import frlp.utn.edu.ar.repository.SeccionARepository;
import frlp.utn.edu.ar.service.SeccionAService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SeccionA}.
 */
@Service
@Transactional
public class SeccionAServiceImpl implements SeccionAService {

    private final Logger log = LoggerFactory.getLogger(SeccionAServiceImpl.class);

    private final SeccionARepository seccionARepository;

    public SeccionAServiceImpl(SeccionARepository seccionARepository) {
        this.seccionARepository = seccionARepository;
    }

    @Override
    public SeccionA save(SeccionA seccionA) {
        log.debug("Request to save SeccionA : {}", seccionA);
        return seccionARepository.save(seccionA);
    }

    @Override
    public Optional<SeccionA> partialUpdate(SeccionA seccionA) {
        log.debug("Request to partially update SeccionA : {}", seccionA);

        return seccionARepository
            .findById(seccionA.getId())
            .map(
                existingSeccionA -> {
                    if (seccionA.getDescripcion() != null) {
                        existingSeccionA.setDescripcion(seccionA.getDescripcion());
                    }
                    if (seccionA.getChequeado() != null) {
                        existingSeccionA.setChequeado(seccionA.getChequeado());
                    }

                    return existingSeccionA;
                }
            )
            .map(seccionARepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SeccionA> findAll(Pageable pageable) {
        log.debug("Request to get all SeccionAS");
        return seccionARepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeccionA> findOne(Long id) {
        log.debug("Request to get SeccionA : {}", id);
        return seccionARepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeccionA : {}", id);
        seccionARepository.deleteById(id);
    }
}
