package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.domain.FraseCompleja;
import frlp.utn.edu.ar.repository.FraseComplejaRepository;
import frlp.utn.edu.ar.service.FraseComplejaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FraseCompleja}.
 */
@Service
@Transactional
public class FraseComplejaServiceImpl implements FraseComplejaService {

    private final Logger log = LoggerFactory.getLogger(FraseComplejaServiceImpl.class);

    private final FraseComplejaRepository fraseComplejaRepository;

    public FraseComplejaServiceImpl(FraseComplejaRepository fraseComplejaRepository) {
        this.fraseComplejaRepository = fraseComplejaRepository;
    }

    @Override
    public FraseCompleja save(FraseCompleja fraseCompleja) {
        log.debug("Request to save FraseCompleja : {}", fraseCompleja);
        return fraseComplejaRepository.save(fraseCompleja);
    }

    @Override
    public Optional<FraseCompleja> partialUpdate(FraseCompleja fraseCompleja) {
        log.debug("Request to partially update FraseCompleja : {}", fraseCompleja);

        return fraseComplejaRepository
            .findById(fraseCompleja.getId())
            .map(
                existingFraseCompleja -> {
                    if (fraseCompleja.getFrase() != null) {
                        existingFraseCompleja.setFrase(fraseCompleja.getFrase());
                    }

                    return existingFraseCompleja;
                }
            )
            .map(fraseComplejaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraseCompleja> findAll() {
        log.debug("Request to get all FraseComplejas");
        return fraseComplejaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FraseCompleja> findOne(Long id) {
        log.debug("Request to get FraseCompleja : {}", id);
        return fraseComplejaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FraseCompleja : {}", id);
        fraseComplejaRepository.deleteById(id);
    }
}
