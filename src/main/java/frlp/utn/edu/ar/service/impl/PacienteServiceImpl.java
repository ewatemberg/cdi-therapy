package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.service.PacienteService;
import frlp.utn.edu.ar.domain.Paciente;
import frlp.utn.edu.ar.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Paciente}.
 */
@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private final Logger log = LoggerFactory.getLogger(PacienteServiceImpl.class);

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente save(Paciente paciente) {
        log.debug("Request to save Paciente : {}", paciente);
        return pacienteRepository.save(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findAll(Pageable pageable) {
        log.debug("Request to get all Pacientes");
        return pacienteRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> findOne(Long id) {
        log.debug("Request to get Paciente : {}", id);
        return pacienteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paciente : {}", id);
        pacienteRepository.deleteById(id);
    }
}
