package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.domain.Cuestionario;
import frlp.utn.edu.ar.repository.CuestionarioRepository;
import frlp.utn.edu.ar.service.CuestionarioService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cuestionario}.
 */
@Service
@Transactional
public class CuestionarioServiceImpl implements CuestionarioService {

    private final Logger log = LoggerFactory.getLogger(CuestionarioServiceImpl.class);

    private final CuestionarioRepository cuestionarioRepository;

    public CuestionarioServiceImpl(CuestionarioRepository cuestionarioRepository) {
        this.cuestionarioRepository = cuestionarioRepository;
    }

    @Override
    public Cuestionario save(Cuestionario cuestionario) {
        log.debug("Request to save Cuestionario : {}", cuestionario);
        return cuestionarioRepository.save(cuestionario);
    }

    @Override
    public Optional<Cuestionario> partialUpdate(Cuestionario cuestionario) {
        log.debug("Request to partially update Cuestionario : {}", cuestionario);

        return cuestionarioRepository
            .findById(cuestionario.getId())
            .map(
                existingCuestionario -> {
                    return existingCuestionario;
                }
            )
            .map(cuestionarioRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cuestionario> findAll(Pageable pageable) {
        log.debug("Request to get all Cuestionarios");
        return cuestionarioRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cuestionario> findOne(Long id) {
        log.debug("Request to get Cuestionario : {}", id);
        return cuestionarioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cuestionario : {}", id);
        cuestionarioRepository.deleteById(id);
    }
}
