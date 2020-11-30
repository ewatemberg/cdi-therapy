package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.service.SeccionBService;
import frlp.utn.edu.ar.domain.SeccionB;
import frlp.utn.edu.ar.repository.SeccionBRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SeccionB}.
 */
@Service
@Transactional
public class SeccionBServiceImpl implements SeccionBService {

    private final Logger log = LoggerFactory.getLogger(SeccionBServiceImpl.class);

    private final SeccionBRepository seccionBRepository;

    public SeccionBServiceImpl(SeccionBRepository seccionBRepository) {
        this.seccionBRepository = seccionBRepository;
    }

    @Override
    public SeccionB save(SeccionB seccionB) {
        log.debug("Request to save SeccionB : {}", seccionB);
        return seccionBRepository.save(seccionB);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SeccionB> findAll(Pageable pageable) {
        log.debug("Request to get all SeccionBS");
        return seccionBRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SeccionB> findOne(Long id) {
        log.debug("Request to get SeccionB : {}", id);
        return seccionBRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeccionB : {}", id);
        seccionBRepository.deleteById(id);
    }
}
