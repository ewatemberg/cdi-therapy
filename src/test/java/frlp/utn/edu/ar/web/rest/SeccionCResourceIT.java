package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.CdiApp;
import frlp.utn.edu.ar.domain.SeccionC;
import frlp.utn.edu.ar.repository.SeccionCRepository;
import frlp.utn.edu.ar.service.SeccionCService;

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
 * Integration tests for the {@link SeccionCResource} REST controller.
 */
@SpringBootTest(classes = CdiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SeccionCResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    @Autowired
    private SeccionCRepository seccionCRepository;

    @Autowired
    private SeccionCService seccionCService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeccionCMockMvc;

    private SeccionC seccionC;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionC createEntity(EntityManager em) {
        SeccionC seccionC = new SeccionC()
            .descripcion(DEFAULT_DESCRIPCION)
            .valor(DEFAULT_VALOR);
        return seccionC;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionC createUpdatedEntity(EntityManager em) {
        SeccionC seccionC = new SeccionC()
            .descripcion(UPDATED_DESCRIPCION)
            .valor(UPDATED_VALOR);
        return seccionC;
    }

    @BeforeEach
    public void initTest() {
        seccionC = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeccionC() throws Exception {
        int databaseSizeBeforeCreate = seccionCRepository.findAll().size();
        // Create the SeccionC
        restSeccionCMockMvc.perform(post("/api/seccion-cs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionC)))
            .andExpect(status().isCreated());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeCreate + 1);
        SeccionC testSeccionC = seccionCList.get(seccionCList.size() - 1);
        assertThat(testSeccionC.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSeccionC.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createSeccionCWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seccionCRepository.findAll().size();

        // Create the SeccionC with an existing ID
        seccionC.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionCMockMvc.perform(post("/api/seccion-cs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionC)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSeccionCS() throws Exception {
        // Initialize the database
        seccionCRepository.saveAndFlush(seccionC);

        // Get all the seccionCList
        restSeccionCMockMvc.perform(get("/api/seccion-cs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccionC.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getSeccionC() throws Exception {
        // Initialize the database
        seccionCRepository.saveAndFlush(seccionC);

        // Get the seccionC
        restSeccionCMockMvc.perform(get("/api/seccion-cs/{id}", seccionC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seccionC.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }
    @Test
    @Transactional
    public void getNonExistingSeccionC() throws Exception {
        // Get the seccionC
        restSeccionCMockMvc.perform(get("/api/seccion-cs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeccionC() throws Exception {
        // Initialize the database
        seccionCService.save(seccionC);

        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();

        // Update the seccionC
        SeccionC updatedSeccionC = seccionCRepository.findById(seccionC.getId()).get();
        // Disconnect from session so that the updates on updatedSeccionC are not directly saved in db
        em.detach(updatedSeccionC);
        updatedSeccionC
            .descripcion(UPDATED_DESCRIPCION)
            .valor(UPDATED_VALOR);

        restSeccionCMockMvc.perform(put("/api/seccion-cs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeccionC)))
            .andExpect(status().isOk());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeUpdate);
        SeccionC testSeccionC = seccionCList.get(seccionCList.size() - 1);
        assertThat(testSeccionC.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSeccionC.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingSeccionC() throws Exception {
        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionCMockMvc.perform(put("/api/seccion-cs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seccionC)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeccionC() throws Exception {
        // Initialize the database
        seccionCService.save(seccionC);

        int databaseSizeBeforeDelete = seccionCRepository.findAll().size();

        // Delete the seccionC
        restSeccionCMockMvc.perform(delete("/api/seccion-cs/{id}", seccionC.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
