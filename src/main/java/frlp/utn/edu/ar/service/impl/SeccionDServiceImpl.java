package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.domain.SeccionD;
import frlp.utn.edu.ar.repository.SeccionDRepository;
import frlp.utn.edu.ar.service.SeccionDService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SeccionD}.
 */
@Service
@Transactional
public class SeccionDServiceImpl implements SeccionDService {

    private final Logger log = LoggerFactory.getLogger(SeccionDServiceImpl.class);

    private final SeccionDRepository seccionDRepository;

    public SeccionDServiceImpl(SeccionDRepository seccionDRepository) {
        this.seccionDRepository = seccionDRepository;
    }

    @Override
    public SeccionD save(SeccionD seccionD) {
        log.debug("Request to save SeccionD : {}", seccionD);
        return seccionDRepository.save(seccionD);
    }

    @Override
    public Optional<SeccionD> partialUpdate(SeccionD seccionD) {
        log.debug("Request to partially update SeccionD : {}", seccionD);

        return seccionDRepository
            .findById(seccionD.getId())
            .map(
                existingSeccionD -> {
                    if (seccionD.getValor() != null) {
                        existingSeccionD.setValor(seccionD.getValor());
                    }

                    return existingSeccionD;
                }
            )
            .map(seccionDRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SeccionD> findAll(Pageable pageable) {
        log.debug("Request to get all SeccionDS");
        return seccionDRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeccionD> findOne(Long id) {
        log.debug("Request to get SeccionD : {}", id);
        return seccionDRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeccionD : {}", id);
        seccionDRepository.deleteById(id);
    }
}
