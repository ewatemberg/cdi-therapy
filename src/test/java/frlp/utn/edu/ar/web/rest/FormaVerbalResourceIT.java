package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.CdiApp;
import frlp.utn.edu.ar.domain.FormaVerbal;
import frlp.utn.edu.ar.repository.FormaVerbalRepository;
import frlp.utn.edu.ar.service.FormaVerbalService;

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
 * Integration tests for the {@link FormaVerbalResource} REST controller.
 */
@SpringBootTest(classes = CdiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FormaVerbalResourceIT {

    private static final String DEFAULT_FORMA = "AAAAAAAAAA";
    private static final String UPDATED_FORMA = "BBBBBBBBBB";

    @Autowired
    private FormaVerbalRepository formaVerbalRepository;

    @Autowired
    private FormaVerbalService formaVerbalService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormaVerbalMockMvc;

    private FormaVerbal formaVerbal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaVerbal createEntity(EntityManager em) {
        FormaVerbal formaVerbal = new FormaVerbal()
            .forma(DEFAULT_FORMA);
        return formaVerbal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaVerbal createUpdatedEntity(EntityManager em) {
        FormaVerbal formaVerbal = new FormaVerbal()
            .forma(UPDATED_FORMA);
        return formaVerbal;
    }

    @BeforeEach
    public void initTest() {
        formaVerbal = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormaVerbal() throws Exception {
        int databaseSizeBeforeCreate = formaVerbalRepository.findAll().size();
        // Create the FormaVerbal
        restFormaVerbalMockMvc.perform(post("/api/forma-verbals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formaVerbal)))
            .andExpect(status().isCreated());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeCreate + 1);
        FormaVerbal testFormaVerbal = formaVerbalList.get(formaVerbalList.size() - 1);
        assertThat(testFormaVerbal.getForma()).isEqualTo(DEFAULT_FORMA);
    }

    @Test
    @Transactional
    public void createFormaVerbalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formaVerbalRepository.findAll().size();

        // Create the FormaVerbal with an existing ID
        formaVerbal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormaVerbalMockMvc.perform(post("/api/forma-verbals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formaVerbal)))
            .andExpect(status().isBadRequest());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFormaVerbals() throws Exception {
        // Initialize the database
        formaVerbalRepository.saveAndFlush(formaVerbal);

        // Get all the formaVerbalList
        restFormaVerbalMockMvc.perform(get("/api/forma-verbals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaVerbal.getId().intValue())))
            .andExpect(jsonPath("$.[*].forma").value(hasItem(DEFAULT_FORMA)));
    }
    
    @Test
    @Transactional
    public void getFormaVerbal() throws Exception {
        // Initialize the database
        formaVerbalRepository.saveAndFlush(formaVerbal);

        // Get the formaVerbal
        restFormaVerbalMockMvc.perform(get("/api/forma-verbals/{id}", formaVerbal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formaVerbal.getId().intValue()))
            .andExpect(jsonPath("$.forma").value(DEFAULT_FORMA));
    }
    @Test
    @Transactional
    public void getNonExistingFormaVerbal() throws Exception {
        // Get the formaVerbal
        restFormaVerbalMockMvc.perform(get("/api/forma-verbals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormaVerbal() throws Exception {
        // Initialize the database
        formaVerbalService.save(formaVerbal);

        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();

        // Update the formaVerbal
        FormaVerbal updatedFormaVerbal = formaVerbalRepository.findById(formaVerbal.getId()).get();
        // Disconnect from session so that the updates on updatedFormaVerbal are not directly saved in db
        em.detach(updatedFormaVerbal);
        updatedFormaVerbal
            .forma(UPDATED_FORMA);

        restFormaVerbalMockMvc.perform(put("/api/forma-verbals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormaVerbal)))
            .andExpect(status().isOk());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
        FormaVerbal testFormaVerbal = formaVerbalList.get(formaVerbalList.size() - 1);
        assertThat(testFormaVerbal.getForma()).isEqualTo(UPDATED_FORMA);
    }

    @Test
    @Transactional
    public void updateNonExistingFormaVerbal() throws Exception {
        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaVerbalMockMvc.perform(put("/api/forma-verbals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formaVerbal)))
            .andExpect(status().isBadRequest());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormaVerbal() throws Exception {
        // Initialize the database
        formaVerbalService.save(formaVerbal);

        int databaseSizeBeforeDelete = formaVerbalRepository.findAll().size();

        // Delete the formaVerbal
        restFormaVerbalMockMvc.perform(delete("/api/forma-verbals/{id}", formaVerbal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
