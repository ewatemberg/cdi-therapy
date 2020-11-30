package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.CdiApp;
import frlp.utn.edu.ar.domain.SeccionA;
import frlp.utn.edu.ar.repository.SeccionARepository;
import frlp.utn.edu.ar.service.SeccionAService;

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
 * Integration tests for the {@link SeccionAResource} REST controller.
 */
@SpringBootTest(classes = CdiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SeccionAResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHEQUEADO = false;
    private static final Boolean UPDATED_CHEQUEADO = true;

    @Autowired
    private SeccionARepository seccionARepository;

    @Autowired
    private SeccionAService seccionAService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeccionAMockMvc;

    private SeccionA seccionA;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionA createEntity(EntityManager em) {
        SeccionA seccionA = new SeccionA()
            .descripcion(DEFAULT_DESCRIPCION)
            .chequeado(DEFAULT_CHEQUEADO);
        return seccionA;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionA createUpdatedEntity(EntityManager em) {
        SeccionA seccionA = new SeccionA()
            .descripcion(UPDATED_DESCRIPCION)
            .chequeado(UPDATED_CHEQUEADO);
        return seccionA;
    }

    @BeforeEach
    public void initTest() {
        seccionA = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeccionA() throws Exception {
        int databaseSizeBeforeCreate = seccionARepository.findAll().size();
        // Create the SeccionA
        restSeccionAMockMvc.perform(post("/api/seccion-as")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionA)))
            .andExpect(status().isCreated());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeCreate + 1);
        SeccionA testSeccionA = seccionAList.get(seccionAList.size() - 1);
        assertThat(testSeccionA.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSeccionA.isChequeado()).isEqualTo(DEFAULT_CHEQUEADO);
    }

    @Test
    @Transactional
    public void createSeccionAWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seccionARepository.findAll().size();

        // Create the SeccionA with an existing ID
        seccionA.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionAMockMvc.perform(post("/api/seccion-as")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionA)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSeccionAS() throws Exception {
        // Initialize the database
        seccionARepository.saveAndFlush(seccionA);

        // Get all the seccionAList
        restSeccionAMockMvc.perform(get("/api/seccion-as?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccionA.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].chequeado").value(hasItem(DEFAULT_CHEQUEADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSeccionA() throws Exception {
        // Initialize the database
        seccionARepository.saveAndFlush(seccionA);

        // Get the seccionA
        restSeccionAMockMvc.perform(get("/api/seccion-as/{id}", seccionA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seccionA.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.chequeado").value(DEFAULT_CHEQUEADO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSeccionA() throws Exception {
        // Get the seccionA
        restSeccionAMockMvc.perform(get("/api/seccion-as/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeccionA() throws Exception {
        // Initialize the database
        seccionAService.save(seccionA);

        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();

        // Update the seccionA
        SeccionA updatedSeccionA = seccionARepository.findById(seccionA.getId()).get();
        // Disconnect from session so that the updates on updatedSeccionA are not directly saved in db
        em.detach(updatedSeccionA);
        updatedSeccionA
            .descripcion(UPDATED_DESCRIPCION)
            .chequeado(UPDATED_CHEQUEADO);

        restSeccionAMockMvc.perform(put("/api/seccion-as")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeccionA)))
            .andExpect(status().isOk());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
        SeccionA testSeccionA = seccionAList.get(seccionAList.size() - 1);
        assertThat(testSeccionA.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSeccionA.isChequeado()).isEqualTo(UPDATED_CHEQUEADO);
    }

    @Test
    @Transactional
    public void updateNonExistingSeccionA() throws Exception {
        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionAMockMvc.perform(put("/api/seccion-as")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionA)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeccionA() throws Exception {
        // Initialize the database
        seccionAService.save(seccionA);

        int databaseSizeBeforeDelete = seccionARepository.findAll().size();

        // Delete the seccionA
        restSeccionAMockMvc.perform(delete("/api/seccion-as/{id}", seccionA.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
