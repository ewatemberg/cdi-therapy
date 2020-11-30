package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.CdiApp;
import frlp.utn.edu.ar.domain.FraseCompleja;
import frlp.utn.edu.ar.repository.FraseComplejaRepository;
import frlp.utn.edu.ar.service.FraseComplejaService;

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
 * Integration tests for the {@link FraseComplejaResource} REST controller.
 */
@SpringBootTest(classes = CdiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FraseComplejaResourceIT {

    private static final String DEFAULT_FRASE = "AAAAAAAAAA";
    private static final String UPDATED_FRASE = "BBBBBBBBBB";

    @Autowired
    private FraseComplejaRepository fraseComplejaRepository;

    @Autowired
    private FraseComplejaService fraseComplejaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFraseComplejaMockMvc;

    private FraseCompleja fraseCompleja;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FraseCompleja createEntity(EntityManager em) {
        FraseCompleja fraseCompleja = new FraseCompleja()
            .frase(DEFAULT_FRASE);
        return fraseCompleja;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FraseCompleja createUpdatedEntity(EntityManager em) {
        FraseCompleja fraseCompleja = new FraseCompleja()
            .frase(UPDATED_FRASE);
        return fraseCompleja;
    }

    @BeforeEach
    public void initTest() {
        fraseCompleja = createEntity(em);
    }

    @Test
    @Transactional
    public void createFraseCompleja() throws Exception {
        int databaseSizeBeforeCreate = fraseComplejaRepository.findAll().size();
        // Create the FraseCompleja
        restFraseComplejaMockMvc.perform(post("/api/frase-complejas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fraseCompleja)))
            .andExpect(status().isCreated());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeCreate + 1);
        FraseCompleja testFraseCompleja = fraseComplejaList.get(fraseComplejaList.size() - 1);
        assertThat(testFraseCompleja.getFrase()).isEqualTo(DEFAULT_FRASE);
    }

    @Test
    @Transactional
    public void createFraseComplejaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fraseComplejaRepository.findAll().size();

        // Create the FraseCompleja with an existing ID
        fraseCompleja.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraseComplejaMockMvc.perform(post("/api/frase-complejas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fraseCompleja)))
            .andExpect(status().isBadRequest());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFraseComplejas() throws Exception {
        // Initialize the database
        fraseComplejaRepository.saveAndFlush(fraseCompleja);

        // Get all the fraseComplejaList
        restFraseComplejaMockMvc.perform(get("/api/frase-complejas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraseCompleja.getId().intValue())))
            .andExpect(jsonPath("$.[*].frase").value(hasItem(DEFAULT_FRASE)));
    }
    
    @Test
    @Transactional
    public void getFraseCompleja() throws Exception {
        // Initialize the database
        fraseComplejaRepository.saveAndFlush(fraseCompleja);

        // Get the fraseCompleja
        restFraseComplejaMockMvc.perform(get("/api/frase-complejas/{id}", fraseCompleja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fraseCompleja.getId().intValue()))
            .andExpect(jsonPath("$.frase").value(DEFAULT_FRASE));
    }
    @Test
    @Transactional
    public void getNonExistingFraseCompleja() throws Exception {
        // Get the fraseCompleja
        restFraseComplejaMockMvc.perform(get("/api/frase-complejas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFraseCompleja() throws Exception {
        // Initialize the database
        fraseComplejaService.save(fraseCompleja);

        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();

        // Update the fraseCompleja
        FraseCompleja updatedFraseCompleja = fraseComplejaRepository.findById(fraseCompleja.getId()).get();
        // Disconnect from session so that the updates on updatedFraseCompleja are not directly saved in db
        em.detach(updatedFraseCompleja);
        updatedFraseCompleja
            .frase(UPDATED_FRASE);

        restFraseComplejaMockMvc.perform(put("/api/frase-complejas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFraseCompleja)))
            .andExpect(status().isOk());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
        FraseCompleja testFraseCompleja = fraseComplejaList.get(fraseComplejaList.size() - 1);
        assertThat(testFraseCompleja.getFrase()).isEqualTo(UPDATED_FRASE);
    }

    @Test
    @Transactional
    public void updateNonExistingFraseCompleja() throws Exception {
        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraseComplejaMockMvc.perform(put("/api/frase-complejas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fraseCompleja)))
            .andExpect(status().isBadRequest());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFraseCompleja() throws Exception {
        // Initialize the database
        fraseComplejaService.save(fraseCompleja);

        int databaseSizeBeforeDelete = fraseComplejaRepository.findAll().size();

        // Delete the fraseCompleja
        restFraseComplejaMockMvc.perform(delete("/api/frase-complejas/{id}", fraseCompleja.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
