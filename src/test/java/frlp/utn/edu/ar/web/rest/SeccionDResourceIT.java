package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.CdiApp;
import frlp.utn.edu.ar.domain.SeccionD;
import frlp.utn.edu.ar.repository.SeccionDRepository;
import frlp.utn.edu.ar.service.SeccionDService;

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
 * Integration tests for the {@link SeccionDResource} REST controller.
 */
@SpringBootTest(classes = CdiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SeccionDResourceIT {

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    @Autowired
    private SeccionDRepository seccionDRepository;

    @Autowired
    private SeccionDService seccionDService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeccionDMockMvc;

    private SeccionD seccionD;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionD createEntity(EntityManager em) {
        SeccionD seccionD = new SeccionD()
            .valor(DEFAULT_VALOR);
        return seccionD;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionD createUpdatedEntity(EntityManager em) {
        SeccionD seccionD = new SeccionD()
            .valor(UPDATED_VALOR);
        return seccionD;
    }

    @BeforeEach
    public void initTest() {
        seccionD = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeccionD() throws Exception {
        int databaseSizeBeforeCreate = seccionDRepository.findAll().size();
        // Create the SeccionD
        restSeccionDMockMvc.perform(post("/api/seccion-ds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionD)))
            .andExpect(status().isCreated());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeCreate + 1);
        SeccionD testSeccionD = seccionDList.get(seccionDList.size() - 1);
        assertThat(testSeccionD.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createSeccionDWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seccionDRepository.findAll().size();

        // Create the SeccionD with an existing ID
        seccionD.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionDMockMvc.perform(post("/api/seccion-ds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionD)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSeccionDS() throws Exception {
        // Initialize the database
        seccionDRepository.saveAndFlush(seccionD);

        // Get all the seccionDList
        restSeccionDMockMvc.perform(get("/api/seccion-ds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccionD.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getSeccionD() throws Exception {
        // Initialize the database
        seccionDRepository.saveAndFlush(seccionD);

        // Get the seccionD
        restSeccionDMockMvc.perform(get("/api/seccion-ds/{id}", seccionD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seccionD.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }
    @Test
    @Transactional
    public void getNonExistingSeccionD() throws Exception {
        // Get the seccionD
        restSeccionDMockMvc.perform(get("/api/seccion-ds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeccionD() throws Exception {
        // Initialize the database
        seccionDService.save(seccionD);

        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();

        // Update the seccionD
        SeccionD updatedSeccionD = seccionDRepository.findById(seccionD.getId()).get();
        // Disconnect from session so that the updates on updatedSeccionD are not directly saved in db
        em.detach(updatedSeccionD);
        updatedSeccionD
            .valor(UPDATED_VALOR);

        restSeccionDMockMvc.perform(put("/api/seccion-ds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeccionD)))
            .andExpect(status().isOk());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
        SeccionD testSeccionD = seccionDList.get(seccionDList.size() - 1);
        assertThat(testSeccionD.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingSeccionD() throws Exception {
        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionDMockMvc.perform(put("/api/seccion-ds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionD)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeccionD() throws Exception {
        // Initialize the database
        seccionDService.save(seccionD);

        int databaseSizeBeforeDelete = seccionDRepository.findAll().size();

        // Delete the seccionD
        restSeccionDMockMvc.perform(delete("/api/seccion-ds/{id}", seccionD.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
