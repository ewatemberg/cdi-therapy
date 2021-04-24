package frlp.utn.edu.ar.service.impl;

import frlp.utn.edu.ar.domain.Paciente;
import frlp.utn.edu.ar.repository.PacienteRepository;
import frlp.utn.edu.ar.service.PacienteService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<Paciente> partialUpdate(Paciente paciente) {
        log.debug("Request to partially update Paciente : {}", paciente);

        return pacienteRepository
            .findById(paciente.getId())
            .map(
                existingPaciente -> {
                    if (paciente.getNombres() != null) {
                        existingPaciente.setNombres(paciente.getNombres());
                    }
                    if (paciente.getApellidos() != null) {
                        existingPaciente.setApellidos(paciente.getApellidos());
                    }
                    if (paciente.getObraSocial() != null) {
                        existingPaciente.setObraSocial(paciente.getObraSocial());
                    }
                    if (paciente.getDni() != null) {
                        existingPaciente.setDni(paciente.getDni());
                    }
                    if (paciente.getFechaNacimiento() != null) {
                        existingPaciente.setFechaNacimiento(paciente.getFechaNacimiento());
                    }
                    if (paciente.getLugarNacimiento() != null) {
                        existingPaciente.setLugarNacimiento(paciente.getLugarNacimiento());
                    }
                    if (paciente.getGenero() != null) {
                        existingPaciente.setGenero(paciente.getGenero());
                    }
                    if (paciente.getNacioAntes9Meses() != null) {
                        existingPaciente.setNacioAntes9Meses(paciente.getNacioAntes9Meses());
                    }
                    if (paciente.getSemanasGestacion() != null) {
                        existingPaciente.setSemanasGestacion(paciente.getSemanasGestacion());
                    }
                    if (paciente.getPesoAlNacer() != null) {
                        existingPaciente.setPesoAlNacer(paciente.getPesoAlNacer());
                    }
                    if (paciente.getEnfermedadAuditivaLenguaje() != null) {
                        existingPaciente.setEnfermedadAuditivaLenguaje(paciente.getEnfermedadAuditivaLenguaje());
                    }
                    if (paciente.getDescripcionProblemaAuditivoLenguaje() != null) {
                        existingPaciente.setDescripcionProblemaAuditivoLenguaje(paciente.getDescripcionProblemaAuditivoLenguaje());
                    }
                    if (paciente.getInfeccionesOido() != null) {
                        existingPaciente.setInfeccionesOido(paciente.getInfeccionesOido());
                    }
                    if (paciente.getTotalInfeccionesAnual() != null) {
                        existingPaciente.setTotalInfeccionesAnual(paciente.getTotalInfeccionesAnual());
                    }
                    if (paciente.getProblemaSalud() != null) {
                        existingPaciente.setProblemaSalud(paciente.getProblemaSalud());
                    }
                    if (paciente.getDescripcionProblemaSalud() != null) {
                        existingPaciente.setDescripcionProblemaSalud(paciente.getDescripcionProblemaSalud());
                    }
                    if (paciente.getNombreMadre() != null) {
                        existingPaciente.setNombreMadre(paciente.getNombreMadre());
                    }
                    if (paciente.getEdadMadre() != null) {
                        existingPaciente.setEdadMadre(paciente.getEdadMadre());
                    }
                    if (paciente.getLugarOrigenMadre() != null) {
                        existingPaciente.setLugarOrigenMadre(paciente.getLugarOrigenMadre());
                    }
                    if (paciente.getNombrePadre() != null) {
                        existingPaciente.setNombrePadre(paciente.getNombrePadre());
                    }
                    if (paciente.getEdadPadre() != null) {
                        existingPaciente.setEdadPadre(paciente.getEdadPadre());
                    }
                    if (paciente.getLugarOrigenPadre() != null) {
                        existingPaciente.setLugarOrigenPadre(paciente.getLugarOrigenPadre());
                    }

                    return existingPaciente;
                }
            )
            .map(pacienteRepository::save);
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
