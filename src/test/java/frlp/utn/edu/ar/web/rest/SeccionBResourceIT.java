package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.CdiApp;
import frlp.utn.edu.ar.domain.SeccionB;
import frlp.utn.edu.ar.repository.SeccionBRepository;
import frlp.utn.edu.ar.service.SeccionBService;

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
 * Integration tests for the {@link SeccionBResource} REST controller.
 */
@SpringBootTest(classes = CdiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SeccionBResourceIT {

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    @Autowired
    private SeccionBRepository seccionBRepository;

    @Autowired
    private SeccionBService seccionBService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeccionBMockMvc;

    private SeccionB seccionB;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionB createEntity(EntityManager em) {
        SeccionB seccionB = new SeccionB()
            .valor(DEFAULT_VALOR);
        return seccionB;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionB createUpdatedEntity(EntityManager em) {
        SeccionB seccionB = new SeccionB()
            .valor(UPDATED_VALOR);
        return seccionB;
    }

    @BeforeEach
    public void initTest() {
        seccionB = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeccionB() throws Exception {
        int databaseSizeBeforeCreate = seccionBRepository.findAll().size();
        // Create the SeccionB
        restSeccionBMockMvc.perform(post("/api/seccion-bs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionB)))
            .andExpect(status().isCreated());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeCreate + 1);
        SeccionB testSeccionB = seccionBList.get(seccionBList.size() - 1);
        assertThat(testSeccionB.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createSeccionBWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seccionBRepository.findAll().size();

        // Create the SeccionB with an existing ID
        seccionB.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionBMockMvc.perform(post("/api/seccion-bs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionB)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSeccionBS() throws Exception {
        // Initialize the database
        seccionBRepository.saveAndFlush(seccionB);

        // Get all the seccionBList
        restSeccionBMockMvc.perform(get("/api/seccion-bs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccionB.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getSeccionB() throws Exception {
        // Initialize the database
        seccionBRepository.saveAndFlush(seccionB);

        // Get the seccionB
        restSeccionBMockMvc.perform(get("/api/seccion-bs/{id}", seccionB.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seccionB.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }
    @Test
    @Transactional
    public void getNonExistingSeccionB() throws Exception {
        // Get the seccionB
        restSeccionBMockMvc.perform(get("/api/seccion-bs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeccionB() throws Exception {
        // Initialize the database
        seccionBService.save(seccionB);

        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();

        // Update the seccionB
        SeccionB updatedSeccionB = seccionBRepository.findById(seccionB.getId()).get();
        // Disconnect from session so that the updates on updatedSeccionB are not directly saved in db
        em.detach(updatedSeccionB);
        updatedSeccionB
            .valor(UPDATED_VALOR);

        restSeccionBMockMvc.perform(put("/api/seccion-bs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeccionB)))
            .andExpect(status().isOk());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
        SeccionB testSeccionB = seccionBList.get(seccionBList.size() - 1);
        assertThat(testSeccionB.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingSeccionB() throws Exception {
        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionBMockMvc.perform(put("/api/seccion-bs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionB)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeccionB() throws Exception {
        // Initialize the database
        seccionBService.save(seccionB);

        int databaseSizeBeforeDelete = seccionBRepository.findAll().size();

        // Delete the seccionB
        restSeccionBMockMvc.perform(delete("/api/seccion-bs/{id}", seccionB.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
