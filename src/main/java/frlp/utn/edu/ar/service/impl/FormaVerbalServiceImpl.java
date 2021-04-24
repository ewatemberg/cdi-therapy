package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.domain.FormaVerbal;
import frlp.utn.edu.ar.repository.FormaVerbalRepository;
import frlp.utn.edu.ar.service.FormaVerbalService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FormaVerbal}.
 */
@Service
@Transactional
public class FormaVerbalServiceImpl implements FormaVerbalService {

    private final Logger log = LoggerFactory.getLogger(FormaVerbalServiceImpl.class);

    private final FormaVerbalRepository formaVerbalRepository;

    public FormaVerbalServiceImpl(FormaVerbalRepository formaVerbalRepository) {
        this.formaVerbalRepository = formaVerbalRepository;
    }

    @Override
    public FormaVerbal save(FormaVerbal formaVerbal) {
        log.debug("Request to save FormaVerbal : {}", formaVerbal);
        return formaVerbalRepository.save(formaVerbal);
    }

    @Override
    public Optional<FormaVerbal> partialUpdate(FormaVerbal formaVerbal) {
        log.debug("Request to partially update FormaVerbal : {}", formaVerbal);

        return formaVerbalRepository
            .findById(formaVerbal.getId())
            .map(
                existingFormaVerbal -> {
                    if (formaVerbal.getForma() != null) {
                        existingFormaVerbal.setForma(formaVerbal.getForma());
                    }

                    return existingFormaVerbal;
                }
            )
            .map(formaVerbalRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormaVerbal> findAll() {
        log.debug("Request to get all FormaVerbals");
        return formaVerbalRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormaVerbal> findOne(Long id) {
        log.debug("Request to get FormaVerbal : {}", id);
        return formaVerbalRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormaVerbal : {}", id);
        formaVerbalRepository.deleteById(id);
    }
}
