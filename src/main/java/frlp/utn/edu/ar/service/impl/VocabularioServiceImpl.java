package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.service.VocabularioService;
import frlp.utn.edu.ar.domain.Vocabulario;
import frlp.utn.edu.ar.repository.VocabularioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Vocabulario}.
 */
@Service
@Transactional
public class VocabularioServiceImpl implements VocabularioService {

    private final Logger log = LoggerFactory.getLogger(VocabularioServiceImpl.class);

    private final VocabularioRepository vocabularioRepository;

    public VocabularioServiceImpl(VocabularioRepository vocabularioRepository) {
        this.vocabularioRepository = vocabularioRepository;
    }

    @Override
    public Vocabulario save(Vocabulario vocabulario) {
        log.debug("Request to save Vocabulario : {}", vocabulario);
        return vocabularioRepository.save(vocabulario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vocabulario> findAll() {
        log.debug("Request to get all Vocabularios");
        return vocabularioRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Vocabulario> findOne(Long id) {
        log.debug("Request to get Vocabulario : {}", id);
        return vocabularioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vocabulario : {}", id);
        vocabularioRepository.deleteById(id);
    }
}
