package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.CdiApp;
import frlp.utn.edu.ar.domain.Cuestionario;
import frlp.utn.edu.ar.repository.CuestionarioRepository;
import frlp.utn.edu.ar.service.CuestionarioService;

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
 * Integration tests for the {@link CuestionarioResource} REST controller.
 */
@SpringBootTest(classes = CdiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CuestionarioResourceIT {

    @Autowired
    private CuestionarioRepository cuestionarioRepository;

    @Autowired
    private CuestionarioService cuestionarioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCuestionarioMockMvc;

    private Cuestionario cuestionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuestionario createEntity(EntityManager em) {
        Cuestionario cuestionario = new Cuestionario();
        return cuestionario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuestionario createUpdatedEntity(EntityManager em) {
        Cuestionario cuestionario = new Cuestionario();
        return cuestionario;
    }

    @BeforeEach
    public void initTest() {
        cuestionario = createEntity(em);
    }

    @Test
    @Transactional
    public void createCuestionario() throws Exception {
        int databaseSizeBeforeCreate = cuestionarioRepository.findAll().size();
        // Create the Cuestionario
        restCuestionarioMockMvc.perform(post("/api/cuestionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cuestionario)))
            .andExpect(status().isCreated());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeCreate + 1);
        Cuestionario testCuestionario = cuestionarioList.get(cuestionarioList.size() - 1);
    }

    @Test
    @Transactional
    public void createCuestionarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cuestionarioRepository.findAll().size();

        // Create the Cuestionario with an existing ID
        cuestionario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuestionarioMockMvc.perform(post("/api/cuestionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cuestionario)))
            .andExpect(status().isBadRequest());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCuestionarios() throws Exception {
        // Initialize the database
        cuestionarioRepository.saveAndFlush(cuestionario);

        // Get all the cuestionarioList
        restCuestionarioMockMvc.perform(get("/api/cuestionarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuestionario.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCuestionario() throws Exception {
        // Initialize the database
        cuestionarioRepository.saveAndFlush(cuestionario);

        // Get the cuestionario
        restCuestionarioMockMvc.perform(get("/api/cuestionarios/{id}", cuestionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cuestionario.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCuestionario() throws Exception {
        // Get the cuestionario
        restCuestionarioMockMvc.perform(get("/api/cuestionarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCuestionario() throws Exception {
        // Initialize the database
        cuestionarioService.save(cuestionario);

        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();

        // Update the cuestionario
        Cuestionario updatedCuestionario = cuestionarioRepository.findById(cuestionario.getId()).get();
        // Disconnect from session so that the updates on updatedCuestionario are not directly saved in db
        em.detach(updatedCuestionario);

        restCuestionarioMockMvc.perform(put("/api/cuestionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCuestionario)))
            .andExpect(status().isOk());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
        Cuestionario testCuestionario = cuestionarioList.get(cuestionarioList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCuestionario() throws Exception {
        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuestionarioMockMvc.perform(put("/api/cuestionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cuestionario)))
            .andExpect(status().isBadRequest());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCuestionario() throws Exception {
        // Initialize the database
        cuestionarioService.save(cuestionario);

        int databaseSizeBeforeDelete = cuestionarioRepository.findAll().size();

        // Delete the cuestionario
        restCuestionarioMockMvc.perform(delete("/api/cuestionarios/{id}", cuestionario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
