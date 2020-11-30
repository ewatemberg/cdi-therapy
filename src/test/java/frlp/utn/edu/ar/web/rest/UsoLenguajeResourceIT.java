package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.CdiApp;
import frlp.utn.edu.ar.domain.UsoLenguaje;
import frlp.utn.edu.ar.repository.UsoLenguajeRepository;
import frlp.utn.edu.ar.service.UsoLenguajeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UsoLenguajeResource} REST controller.
 */
@SpringBootTest(classes = CdiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsoLenguajeResourceIT {

    private static final String DEFAULT_PREGUNTA = "AAAAAAAAAA";
    private static final String UPDATED_PREGUNTA = "BBBBBBBBBB";

    @Autowired
    private UsoLenguajeRepository usoLenguajeRepository;

    @Autowired
    private UsoLenguajeService usoLenguajeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsoLenguajeMockMvc;

    private UsoLenguaje usoLenguaje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsoLenguaje createEntity(EntityManager em) {
        UsoLenguaje usoLenguaje = new UsoLenguaje()
            .pregunta(DEFAULT_PREGUNTA);
        return usoLenguaje;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsoLenguaje createUpdatedEntity(EntityManager em) {
        UsoLenguaje usoLenguaje = new UsoLenguaje()
            .pregunta(UPDATED_PREGUNTA);
        return usoLenguaje;
    }

    @BeforeEach
    public void initTest() {
        usoLenguaje = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsoLenguaje() throws Exception {
        int databaseSizeBeforeCreate = usoLenguajeRepository.findAll().size();
        // Create the UsoLenguaje
        restUsoLenguajeMockMvc.perform(post("/api/uso-lenguajes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usoLenguaje)))
            .andExpect(status().isCreated());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeCreate + 1);
        UsoLenguaje testUsoLenguaje = usoLenguajeList.get(usoLenguajeList.size() - 1);
        assertThat(testUsoLenguaje.getPregunta()).isEqualTo(DEFAULT_PREGUNTA);
    }

    @Test
    @Transactional
    public void createUsoLenguajeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usoLenguajeRepository.findAll().size();

        // Create the UsoLenguaje with an existing ID
        usoLenguaje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsoLenguajeMockMvc.perform(post("/api/uso-lenguajes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usoLenguaje)))
            .andExpect(status().isBadRequest());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUsoLenguajes() throws Exception {
        // Initialize the database
        usoLenguajeRepository.saveAndFlush(usoLenguaje);

        // Get all the usoLenguajeList
        restUsoLenguajeMockMvc.perform(get("/api/uso-lenguajes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usoLenguaje.getId().intValue())))
            .andExpect(jsonPath("$.[*].pregunta").value(hasItem(DEFAULT_PREGUNTA)));
    }
    
    @Test
    @Transactional
    public void getUsoLenguaje() throws Exception {
        // Initialize the database
        usoLenguajeRepository.saveAndFlush(usoLenguaje);

        // Get the usoLenguaje
        restUsoLenguajeMockMvc.perform(get("/api/uso-lenguajes/{id}", usoLenguaje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usoLenguaje.getId().intValue()))
            .andExpect(jsonPath("$.pregunta").value(DEFAULT_PREGUNTA));
    }
    @Test
    @Transactional
    public void getNonExistingUsoLenguaje() throws Exception {
        // Get the usoLenguaje
        restUsoLenguajeMockMvc.perform(get("/api/uso-lenguajes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsoLenguaje() throws Exception {
        // Initialize the database
        usoLenguajeService.save(usoLenguaje);

        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();

        // Update the usoLenguaje
        UsoLenguaje updatedUsoLenguaje = usoLenguajeRepository.findById(usoLenguaje.getId()).get();
        // Disconnect from session so that the updates on updatedUsoLenguaje are not directly saved in db
        em.detach(updatedUsoLenguaje);
        updatedUsoLenguaje
            .pregunta(UPDATED_PREGUNTA);

        restUsoLenguajeMockMvc.perform(put("/api/uso-lenguajes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsoLenguaje)))
            .andExpect(status().isOk());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
        UsoLenguaje testUsoLenguaje = usoLenguajeList.get(usoLenguajeList.size() - 1);
        assertThat(testUsoLenguaje.getPregunta()).isEqualTo(UPDATED_PREGUNTA);
    }

    @Test
    @Transactional
    public void updateNonExistingUsoLenguaje() throws Exception {
        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsoLenguajeMockMvc.perform(put("/api/uso-lenguajes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usoLenguaje)))
            .andExpect(status().isBadRequest());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsoLenguaje() throws Exception {
        // Initialize the database
        usoLenguajeService.save(usoLenguaje);

        int databaseSizeBeforeDelete = usoLenguajeRepository.findAll().size();

        // Delete the usoLenguaje
        restUsoLenguajeMockMvc.perform(delete("/api/uso-lenguajes/{id}", usoLenguaje.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
