package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.service.SeccionCService;
import frlp.utn.edu.ar.domain.SeccionC;
import frlp.utn.edu.ar.repository.SeccionCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
