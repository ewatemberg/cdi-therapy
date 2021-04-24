package frlp.utn.edu.ar.web.rest;

import static frlp.utn.edu.ar.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frlp.utn.edu.ar.IntegrationTest;
import frlp.utn.edu.ar.domain.Paciente;
import frlp.utn.edu.ar.repository.PacienteRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PacienteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PacienteResourceIT {

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_OBRA_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_OBRA_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LUGAR_NACIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR_NACIMIENTO = "BBBBBBBBBB";

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NACIO_ANTES_9_MESES = false;
    private static final Boolean UPDATED_NACIO_ANTES_9_MESES = true;

    private static final Integer DEFAULT_SEMANAS_GESTACION = 1;
    private static final Integer UPDATED_SEMANAS_GESTACION = 2;

    private static final BigDecimal DEFAULT_PESO_AL_NACER = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESO_AL_NACER = new BigDecimal(2);

    private static final Boolean DEFAULT_ENFERMEDAD_AUDITIVA_LENGUAJE = false;
    private static final Boolean UPDATED_ENFERMEDAD_AUDITIVA_LENGUAJE = true;

    private static final String DEFAULT_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INFECCIONES_OIDO = false;
    private static final Boolean UPDATED_INFECCIONES_OIDO = true;

    private static final Integer DEFAULT_TOTAL_INFECCIONES_ANUAL = 1;
    private static final Integer UPDATED_TOTAL_INFECCIONES_ANUAL = 2;

    private static final Boolean DEFAULT_PROBLEMA_SALUD = false;
    private static final Boolean UPDATED_PROBLEMA_SALUD = true;

    private static final String DEFAULT_DESCRIPCION_PROBLEMA_SALUD = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_PROBLEMA_SALUD = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_MADRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_MADRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD_MADRE = 1;
    private static final Integer UPDATED_EDAD_MADRE = 2;

    private static final String DEFAULT_LUGAR_ORIGEN_MADRE = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR_ORIGEN_MADRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_PADRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PADRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD_PADRE = 1;
    private static final Integer UPDATED_EDAD_PADRE = 2;

    private static final String DEFAULT_LUGAR_ORIGEN_PADRE = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR_ORIGEN_PADRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pacientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPacienteMockMvc;

    private Paciente paciente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .obraSocial(DEFAULT_OBRA_SOCIAL)
            .dni(DEFAULT_DNI)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .lugarNacimiento(DEFAULT_LUGAR_NACIMIENTO)
            .genero(DEFAULT_GENERO)
            .nacioAntes9Meses(DEFAULT_NACIO_ANTES_9_MESES)
            .semanasGestacion(DEFAULT_SEMANAS_GESTACION)
            .pesoAlNacer(DEFAULT_PESO_AL_NACER)
            .enfermedadAuditivaLenguaje(DEFAULT_ENFERMEDAD_AUDITIVA_LENGUAJE)
            .descripcionProblemaAuditivoLenguaje(DEFAULT_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE)
            .infeccionesOido(DEFAULT_INFECCIONES_OIDO)
            .totalInfeccionesAnual(DEFAULT_TOTAL_INFECCIONES_ANUAL)
            .problemaSalud(DEFAULT_PROBLEMA_SALUD)
            .descripcionProblemaSalud(DEFAULT_DESCRIPCION_PROBLEMA_SALUD)
            .nombreMadre(DEFAULT_NOMBRE_MADRE)
            .edadMadre(DEFAULT_EDAD_MADRE)
            .lugarOrigenMadre(DEFAULT_LUGAR_ORIGEN_MADRE)
            .nombrePadre(DEFAULT_NOMBRE_PADRE)
            .edadPadre(DEFAULT_EDAD_PADRE)
            .lugarOrigenPadre(DEFAULT_LUGAR_ORIGEN_PADRE);
        return paciente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createUpdatedEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .obraSocial(UPDATED_OBRA_SOCIAL)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .lugarNacimiento(UPDATED_LUGAR_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .nacioAntes9Meses(UPDATED_NACIO_ANTES_9_MESES)
            .semanasGestacion(UPDATED_SEMANAS_GESTACION)
            .pesoAlNacer(UPDATED_PESO_AL_NACER)
            .enfermedadAuditivaLenguaje(UPDATED_ENFERMEDAD_AUDITIVA_LENGUAJE)
            .descripcionProblemaAuditivoLenguaje(UPDATED_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE)
            .infeccionesOido(UPDATED_INFECCIONES_OIDO)
            .totalInfeccionesAnual(UPDATED_TOTAL_INFECCIONES_ANUAL)
            .problemaSalud(UPDATED_PROBLEMA_SALUD)
            .descripcionProblemaSalud(UPDATED_DESCRIPCION_PROBLEMA_SALUD)
            .nombreMadre(UPDATED_NOMBRE_MADRE)
            .edadMadre(UPDATED_EDAD_MADRE)
            .lugarOrigenMadre(UPDATED_LUGAR_ORIGEN_MADRE)
            .nombrePadre(UPDATED_NOMBRE_PADRE)
            .edadPadre(UPDATED_EDAD_PADRE)
            .lugarOrigenPadre(UPDATED_LUGAR_ORIGEN_PADRE);
        return paciente;
    }

    @BeforeEach
    public void initTest() {
        paciente = createEntity(em);
    }

    @Test
    @Transactional
    void createPaciente() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();
        // Create the Paciente
        restPacienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testPaciente.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testPaciente.getObraSocial()).isEqualTo(DEFAULT_OBRA_SOCIAL);
        assertThat(testPaciente.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testPaciente.getLugarNacimiento()).isEqualTo(DEFAULT_LUGAR_NACIMIENTO);
        assertThat(testPaciente.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testPaciente.getNacioAntes9Meses()).isEqualTo(DEFAULT_NACIO_ANTES_9_MESES);
        assertThat(testPaciente.getSemanasGestacion()).isEqualTo(DEFAULT_SEMANAS_GESTACION);
        assertThat(testPaciente.getPesoAlNacer()).isEqualByComparingTo(DEFAULT_PESO_AL_NACER);
        assertThat(testPaciente.getEnfermedadAuditivaLenguaje()).isEqualTo(DEFAULT_ENFERMEDAD_AUDITIVA_LENGUAJE);
        assertThat(testPaciente.getDescripcionProblemaAuditivoLenguaje()).isEqualTo(DEFAULT_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE);
        assertThat(testPaciente.getInfeccionesOido()).isEqualTo(DEFAULT_INFECCIONES_OIDO);
        assertThat(testPaciente.getTotalInfeccionesAnual()).isEqualTo(DEFAULT_TOTAL_INFECCIONES_ANUAL);
        assertThat(testPaciente.getProblemaSalud()).isEqualTo(DEFAULT_PROBLEMA_SALUD);
        assertThat(testPaciente.getDescripcionProblemaSalud()).isEqualTo(DEFAULT_DESCRIPCION_PROBLEMA_SALUD);
        assertThat(testPaciente.getNombreMadre()).isEqualTo(DEFAULT_NOMBRE_MADRE);
        assertThat(testPaciente.getEdadMadre()).isEqualTo(DEFAULT_EDAD_MADRE);
        assertThat(testPaciente.getLugarOrigenMadre()).isEqualTo(DEFAULT_LUGAR_ORIGEN_MADRE);
        assertThat(testPaciente.getNombrePadre()).isEqualTo(DEFAULT_NOMBRE_PADRE);
        assertThat(testPaciente.getEdadPadre()).isEqualTo(DEFAULT_EDAD_PADRE);
        assertThat(testPaciente.getLugarOrigenPadre()).isEqualTo(DEFAULT_LUGAR_ORIGEN_PADRE);
    }

    @Test
    @Transactional
    void createPacienteWithExistingId() throws Exception {
        // Create the Paciente with an existing ID
        paciente.setId(1L);

        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPacientes() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList
        restPacienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].obraSocial").value(hasItem(DEFAULT_OBRA_SOCIAL)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].lugarNacimiento").value(hasItem(DEFAULT_LUGAR_NACIMIENTO)))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO)))
            .andExpect(jsonPath("$.[*].nacioAntes9Meses").value(hasItem(DEFAULT_NACIO_ANTES_9_MESES.booleanValue())))
            .andExpect(jsonPath("$.[*].semanasGestacion").value(hasItem(DEFAULT_SEMANAS_GESTACION)))
            .andExpect(jsonPath("$.[*].pesoAlNacer").value(hasItem(sameNumber(DEFAULT_PESO_AL_NACER))))
            .andExpect(jsonPath("$.[*].enfermedadAuditivaLenguaje").value(hasItem(DEFAULT_ENFERMEDAD_AUDITIVA_LENGUAJE.booleanValue())))
            .andExpect(jsonPath("$.[*].descripcionProblemaAuditivoLenguaje").value(hasItem(DEFAULT_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE)))
            .andExpect(jsonPath("$.[*].infeccionesOido").value(hasItem(DEFAULT_INFECCIONES_OIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].totalInfeccionesAnual").value(hasItem(DEFAULT_TOTAL_INFECCIONES_ANUAL)))
            .andExpect(jsonPath("$.[*].problemaSalud").value(hasItem(DEFAULT_PROBLEMA_SALUD.booleanValue())))
            .andExpect(jsonPath("$.[*].descripcionProblemaSalud").value(hasItem(DEFAULT_DESCRIPCION_PROBLEMA_SALUD)))
            .andExpect(jsonPath("$.[*].nombreMadre").value(hasItem(DEFAULT_NOMBRE_MADRE)))
            .andExpect(jsonPath("$.[*].edadMadre").value(hasItem(DEFAULT_EDAD_MADRE)))
            .andExpect(jsonPath("$.[*].lugarOrigenMadre").value(hasItem(DEFAULT_LUGAR_ORIGEN_MADRE)))
            .andExpect(jsonPath("$.[*].nombrePadre").value(hasItem(DEFAULT_NOMBRE_PADRE)))
            .andExpect(jsonPath("$.[*].edadPadre").value(hasItem(DEFAULT_EDAD_PADRE)))
            .andExpect(jsonPath("$.[*].lugarOrigenPadre").value(hasItem(DEFAULT_LUGAR_ORIGEN_PADRE)));
    }

    @Test
    @Transactional
    void getPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get the paciente
        restPacienteMockMvc
            .perform(get(ENTITY_API_URL_ID, paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.obraSocial").value(DEFAULT_OBRA_SOCIAL))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.lugarNacimiento").value(DEFAULT_LUGAR_NACIMIENTO))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO))
            .andExpect(jsonPath("$.nacioAntes9Meses").value(DEFAULT_NACIO_ANTES_9_MESES.booleanValue()))
            .andExpect(jsonPath("$.semanasGestacion").value(DEFAULT_SEMANAS_GESTACION))
            .andExpect(jsonPath("$.pesoAlNacer").value(sameNumber(DEFAULT_PESO_AL_NACER)))
            .andExpect(jsonPath("$.enfermedadAuditivaLenguaje").value(DEFAULT_ENFERMEDAD_AUDITIVA_LENGUAJE.booleanValue()))
            .andExpect(jsonPath("$.descripcionProblemaAuditivoLenguaje").value(DEFAULT_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE))
            .andExpect(jsonPath("$.infeccionesOido").value(DEFAULT_INFECCIONES_OIDO.booleanValue()))
            .andExpect(jsonPath("$.totalInfeccionesAnual").value(DEFAULT_TOTAL_INFECCIONES_ANUAL))
            .andExpect(jsonPath("$.problemaSalud").value(DEFAULT_PROBLEMA_SALUD.booleanValue()))
            .andExpect(jsonPath("$.descripcionProblemaSalud").value(DEFAULT_DESCRIPCION_PROBLEMA_SALUD))
            .andExpect(jsonPath("$.nombreMadre").value(DEFAULT_NOMBRE_MADRE))
            .andExpect(jsonPath("$.edadMadre").value(DEFAULT_EDAD_MADRE))
            .andExpect(jsonPath("$.lugarOrigenMadre").value(DEFAULT_LUGAR_ORIGEN_MADRE))
            .andExpect(jsonPath("$.nombrePadre").value(DEFAULT_NOMBRE_PADRE))
            .andExpect(jsonPath("$.edadPadre").value(DEFAULT_EDAD_PADRE))
            .andExpect(jsonPath("$.lugarOrigenPadre").value(DEFAULT_LUGAR_ORIGEN_PADRE));
    }

    @Test
    @Transactional
    void getNonExistingPaciente() throws Exception {
        // Get the paciente
        restPacienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente
        Paciente updatedPaciente = pacienteRepository.findById(paciente.getId()).get();
        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
        em.detach(updatedPaciente);
        updatedPaciente
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .obraSocial(UPDATED_OBRA_SOCIAL)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .lugarNacimiento(UPDATED_LUGAR_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .nacioAntes9Meses(UPDATED_NACIO_ANTES_9_MESES)
            .semanasGestacion(UPDATED_SEMANAS_GESTACION)
            .pesoAlNacer(UPDATED_PESO_AL_NACER)
            .enfermedadAuditivaLenguaje(UPDATED_ENFERMEDAD_AUDITIVA_LENGUAJE)
            .descripcionProblemaAuditivoLenguaje(UPDATED_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE)
            .infeccionesOido(UPDATED_INFECCIONES_OIDO)
            .totalInfeccionesAnual(UPDATED_TOTAL_INFECCIONES_ANUAL)
            .problemaSalud(UPDATED_PROBLEMA_SALUD)
            .descripcionProblemaSalud(UPDATED_DESCRIPCION_PROBLEMA_SALUD)
            .nombreMadre(UPDATED_NOMBRE_MADRE)
            .edadMadre(UPDATED_EDAD_MADRE)
            .lugarOrigenMadre(UPDATED_LUGAR_ORIGEN_MADRE)
            .nombrePadre(UPDATED_NOMBRE_PADRE)
            .edadPadre(UPDATED_EDAD_PADRE)
            .lugarOrigenPadre(UPDATED_LUGAR_ORIGEN_PADRE);

        restPacienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaciente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaciente))
            )
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testPaciente.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPaciente.getObraSocial()).isEqualTo(UPDATED_OBRA_SOCIAL);
        assertThat(testPaciente.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPaciente.getLugarNacimiento()).isEqualTo(UPDATED_LUGAR_NACIMIENTO);
        assertThat(testPaciente.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testPaciente.getNacioAntes9Meses()).isEqualTo(UPDATED_NACIO_ANTES_9_MESES);
        assertThat(testPaciente.getSemanasGestacion()).isEqualTo(UPDATED_SEMANAS_GESTACION);
        assertThat(testPaciente.getPesoAlNacer()).isEqualTo(UPDATED_PESO_AL_NACER);
        assertThat(testPaciente.getEnfermedadAuditivaLenguaje()).isEqualTo(UPDATED_ENFERMEDAD_AUDITIVA_LENGUAJE);
        assertThat(testPaciente.getDescripcionProblemaAuditivoLenguaje()).isEqualTo(UPDATED_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE);
        assertThat(testPaciente.getInfeccionesOido()).isEqualTo(UPDATED_INFECCIONES_OIDO);
        assertThat(testPaciente.getTotalInfeccionesAnual()).isEqualTo(UPDATED_TOTAL_INFECCIONES_ANUAL);
        assertThat(testPaciente.getProblemaSalud()).isEqualTo(UPDATED_PROBLEMA_SALUD);
        assertThat(testPaciente.getDescripcionProblemaSalud()).isEqualTo(UPDATED_DESCRIPCION_PROBLEMA_SALUD);
        assertThat(testPaciente.getNombreMadre()).isEqualTo(UPDATED_NOMBRE_MADRE);
        assertThat(testPaciente.getEdadMadre()).isEqualTo(UPDATED_EDAD_MADRE);
        assertThat(testPaciente.getLugarOrigenMadre()).isEqualTo(UPDATED_LUGAR_ORIGEN_MADRE);
        assertThat(testPaciente.getNombrePadre()).isEqualTo(UPDATED_NOMBRE_PADRE);
        assertThat(testPaciente.getEdadPadre()).isEqualTo(UPDATED_EDAD_PADRE);
        assertThat(testPaciente.getLugarOrigenPadre()).isEqualTo(UPDATED_LUGAR_ORIGEN_PADRE);
    }

    @Test
    @Transactional
    void putNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paciente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paciente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paciente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePacienteWithPatch() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente using partial update
        Paciente partialUpdatedPaciente = new Paciente();
        partialUpdatedPaciente.setId(paciente.getId());

        partialUpdatedPaciente
            .apellidos(UPDATED_APELLIDOS)
            .obraSocial(UPDATED_OBRA_SOCIAL)
            .genero(UPDATED_GENERO)
            .nacioAntes9Meses(UPDATED_NACIO_ANTES_9_MESES)
            .pesoAlNacer(UPDATED_PESO_AL_NACER)
            .infeccionesOido(UPDATED_INFECCIONES_OIDO)
            .totalInfeccionesAnual(UPDATED_TOTAL_INFECCIONES_ANUAL)
            .descripcionProblemaSalud(UPDATED_DESCRIPCION_PROBLEMA_SALUD)
            .nombreMadre(UPDATED_NOMBRE_MADRE)
            .lugarOrigenMadre(UPDATED_LUGAR_ORIGEN_MADRE)
            .edadPadre(UPDATED_EDAD_PADRE)
            .lugarOrigenPadre(UPDATED_LUGAR_ORIGEN_PADRE);

        restPacienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaciente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaciente))
            )
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testPaciente.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPaciente.getObraSocial()).isEqualTo(UPDATED_OBRA_SOCIAL);
        assertThat(testPaciente.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testPaciente.getLugarNacimiento()).isEqualTo(DEFAULT_LUGAR_NACIMIENTO);
        assertThat(testPaciente.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testPaciente.getNacioAntes9Meses()).isEqualTo(UPDATED_NACIO_ANTES_9_MESES);
        assertThat(testPaciente.getSemanasGestacion()).isEqualTo(DEFAULT_SEMANAS_GESTACION);
        assertThat(testPaciente.getPesoAlNacer()).isEqualByComparingTo(UPDATED_PESO_AL_NACER);
        assertThat(testPaciente.getEnfermedadAuditivaLenguaje()).isEqualTo(DEFAULT_ENFERMEDAD_AUDITIVA_LENGUAJE);
        assertThat(testPaciente.getDescripcionProblemaAuditivoLenguaje()).isEqualTo(DEFAULT_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE);
        assertThat(testPaciente.getInfeccionesOido()).isEqualTo(UPDATED_INFECCIONES_OIDO);
        assertThat(testPaciente.getTotalInfeccionesAnual()).isEqualTo(UPDATED_TOTAL_INFECCIONES_ANUAL);
        assertThat(testPaciente.getProblemaSalud()).isEqualTo(DEFAULT_PROBLEMA_SALUD);
        assertThat(testPaciente.getDescripcionProblemaSalud()).isEqualTo(UPDATED_DESCRIPCION_PROBLEMA_SALUD);
        assertThat(testPaciente.getNombreMadre()).isEqualTo(UPDATED_NOMBRE_MADRE);
        assertThat(testPaciente.getEdadMadre()).isEqualTo(DEFAULT_EDAD_MADRE);
        assertThat(testPaciente.getLugarOrigenMadre()).isEqualTo(UPDATED_LUGAR_ORIGEN_MADRE);
        assertThat(testPaciente.getNombrePadre()).isEqualTo(DEFAULT_NOMBRE_PADRE);
        assertThat(testPaciente.getEdadPadre()).isEqualTo(UPDATED_EDAD_PADRE);
        assertThat(testPaciente.getLugarOrigenPadre()).isEqualTo(UPDATED_LUGAR_ORIGEN_PADRE);
    }

    @Test
    @Transactional
    void fullUpdatePacienteWithPatch() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente using partial update
        Paciente partialUpdatedPaciente = new Paciente();
        partialUpdatedPaciente.setId(paciente.getId());

        partialUpdatedPaciente
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .obraSocial(UPDATED_OBRA_SOCIAL)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .lugarNacimiento(UPDATED_LUGAR_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .nacioAntes9Meses(UPDATED_NACIO_ANTES_9_MESES)
            .semanasGestacion(UPDATED_SEMANAS_GESTACION)
            .pesoAlNacer(UPDATED_PESO_AL_NACER)
            .enfermedadAuditivaLenguaje(UPDATED_ENFERMEDAD_AUDITIVA_LENGUAJE)
            .descripcionProblemaAuditivoLenguaje(UPDATED_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE)
            .infeccionesOido(UPDATED_INFECCIONES_OIDO)
            .totalInfeccionesAnual(UPDATED_TOTAL_INFECCIONES_ANUAL)
            .problemaSalud(UPDATED_PROBLEMA_SALUD)
            .descripcionProblemaSalud(UPDATED_DESCRIPCION_PROBLEMA_SALUD)
            .nombreMadre(UPDATED_NOMBRE_MADRE)
            .edadMadre(UPDATED_EDAD_MADRE)
            .lugarOrigenMadre(UPDATED_LUGAR_ORIGEN_MADRE)
            .nombrePadre(UPDATED_NOMBRE_PADRE)
            .edadPadre(UPDATED_EDAD_PADRE)
            .lugarOrigenPadre(UPDATED_LUGAR_ORIGEN_PADRE);

        restPacienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaciente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaciente))
            )
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testPaciente.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPaciente.getObraSocial()).isEqualTo(UPDATED_OBRA_SOCIAL);
        assertThat(testPaciente.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPaciente.getLugarNacimiento()).isEqualTo(UPDATED_LUGAR_NACIMIENTO);
        assertThat(testPaciente.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testPaciente.getNacioAntes9Meses()).isEqualTo(UPDATED_NACIO_ANTES_9_MESES);
        assertThat(testPaciente.getSemanasGestacion()).isEqualTo(UPDATED_SEMANAS_GESTACION);
        assertThat(testPaciente.getPesoAlNacer()).isEqualByComparingTo(UPDATED_PESO_AL_NACER);
        assertThat(testPaciente.getEnfermedadAuditivaLenguaje()).isEqualTo(UPDATED_ENFERMEDAD_AUDITIVA_LENGUAJE);
        assertThat(testPaciente.getDescripcionProblemaAuditivoLenguaje()).isEqualTo(UPDATED_DESCRIPCION_PROBLEMA_AUDITIVO_LENGUAJE);
        assertThat(testPaciente.getInfeccionesOido()).isEqualTo(UPDATED_INFECCIONES_OIDO);
        assertThat(testPaciente.getTotalInfeccionesAnual()).isEqualTo(UPDATED_TOTAL_INFECCIONES_ANUAL);
        assertThat(testPaciente.getProblemaSalud()).isEqualTo(UPDATED_PROBLEMA_SALUD);
        assertThat(testPaciente.getDescripcionProblemaSalud()).isEqualTo(UPDATED_DESCRIPCION_PROBLEMA_SALUD);
        assertThat(testPaciente.getNombreMadre()).isEqualTo(UPDATED_NOMBRE_MADRE);
        assertThat(testPaciente.getEdadMadre()).isEqualTo(UPDATED_EDAD_MADRE);
        assertThat(testPaciente.getLugarOrigenMadre()).isEqualTo(UPDATED_LUGAR_ORIGEN_MADRE);
        assertThat(testPaciente.getNombrePadre()).isEqualTo(UPDATED_NOMBRE_PADRE);
        assertThat(testPaciente.getEdadPadre()).isEqualTo(UPDATED_EDAD_PADRE);
        assertThat(testPaciente.getLugarOrigenPadre()).isEqualTo(UPDATED_LUGAR_ORIGEN_PADRE);
    }

    @Test
    @Transactional
    void patchNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paciente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paciente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paciente))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
        paciente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPacienteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();

        // Delete the paciente
        restPacienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, paciente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
