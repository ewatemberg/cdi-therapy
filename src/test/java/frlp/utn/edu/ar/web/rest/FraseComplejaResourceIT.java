package frlp.utn.edu.ar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frlp.utn.edu.ar.IntegrationTest;
import frlp.utn.edu.ar.domain.FraseCompleja;
import frlp.utn.edu.ar.repository.FraseComplejaRepository;
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
 * Integration tests for the {@link FraseComplejaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FraseComplejaResourceIT {

    private static final String DEFAULT_FRASE = "AAAAAAAAAA";
    private static final String UPDATED_FRASE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/frase-complejas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FraseComplejaRepository fraseComplejaRepository;

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
        FraseCompleja fraseCompleja = new FraseCompleja().frase(DEFAULT_FRASE);
        return fraseCompleja;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FraseCompleja createUpdatedEntity(EntityManager em) {
        FraseCompleja fraseCompleja = new FraseCompleja().frase(UPDATED_FRASE);
        return fraseCompleja;
    }

    @BeforeEach
    public void initTest() {
        fraseCompleja = createEntity(em);
    }

    @Test
    @Transactional
    void createFraseCompleja() throws Exception {
        int databaseSizeBeforeCreate = fraseComplejaRepository.findAll().size();
        // Create the FraseCompleja
        restFraseComplejaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fraseCompleja)))
            .andExpect(status().isCreated());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeCreate + 1);
        FraseCompleja testFraseCompleja = fraseComplejaList.get(fraseComplejaList.size() - 1);
        assertThat(testFraseCompleja.getFrase()).isEqualTo(DEFAULT_FRASE);
    }

    @Test
    @Transactional
    void createFraseComplejaWithExistingId() throws Exception {
        // Create the FraseCompleja with an existing ID
        fraseCompleja.setId(1L);

        int databaseSizeBeforeCreate = fraseComplejaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraseComplejaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fraseCompleja)))
            .andExpect(status().isBadRequest());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFraseComplejas() throws Exception {
        // Initialize the database
        fraseComplejaRepository.saveAndFlush(fraseCompleja);

        // Get all the fraseComplejaList
        restFraseComplejaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraseCompleja.getId().intValue())))
            .andExpect(jsonPath("$.[*].frase").value(hasItem(DEFAULT_FRASE)));
    }

    @Test
    @Transactional
    void getFraseCompleja() throws Exception {
        // Initialize the database
        fraseComplejaRepository.saveAndFlush(fraseCompleja);

        // Get the fraseCompleja
        restFraseComplejaMockMvc
            .perform(get(ENTITY_API_URL_ID, fraseCompleja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fraseCompleja.getId().intValue()))
            .andExpect(jsonPath("$.frase").value(DEFAULT_FRASE));
    }

    @Test
    @Transactional
    void getNonExistingFraseCompleja() throws Exception {
        // Get the fraseCompleja
        restFraseComplejaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFraseCompleja() throws Exception {
        // Initialize the database
        fraseComplejaRepository.saveAndFlush(fraseCompleja);

        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();

        // Update the fraseCompleja
        FraseCompleja updatedFraseCompleja = fraseComplejaRepository.findById(fraseCompleja.getId()).get();
        // Disconnect from session so that the updates on updatedFraseCompleja are not directly saved in db
        em.detach(updatedFraseCompleja);
        updatedFraseCompleja.frase(UPDATED_FRASE);

        restFraseComplejaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFraseCompleja.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFraseCompleja))
            )
            .andExpect(status().isOk());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
        FraseCompleja testFraseCompleja = fraseComplejaList.get(fraseComplejaList.size() - 1);
        assertThat(testFraseCompleja.getFrase()).isEqualTo(UPDATED_FRASE);
    }

    @Test
    @Transactional
    void putNonExistingFraseCompleja() throws Exception {
        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();
        fraseCompleja.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraseComplejaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fraseCompleja.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraseCompleja))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFraseCompleja() throws Exception {
        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();
        fraseCompleja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraseComplejaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fraseCompleja))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFraseCompleja() throws Exception {
        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();
        fraseCompleja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraseComplejaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fraseCompleja)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFraseComplejaWithPatch() throws Exception {
        // Initialize the database
        fraseComplejaRepository.saveAndFlush(fraseCompleja);

        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();

        // Update the fraseCompleja using partial update
        FraseCompleja partialUpdatedFraseCompleja = new FraseCompleja();
        partialUpdatedFraseCompleja.setId(fraseCompleja.getId());

        partialUpdatedFraseCompleja.frase(UPDATED_FRASE);

        restFraseComplejaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraseCompleja.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFraseCompleja))
            )
            .andExpect(status().isOk());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
        FraseCompleja testFraseCompleja = fraseComplejaList.get(fraseComplejaList.size() - 1);
        assertThat(testFraseCompleja.getFrase()).isEqualTo(UPDATED_FRASE);
    }

    @Test
    @Transactional
    void fullUpdateFraseComplejaWithPatch() throws Exception {
        // Initialize the database
        fraseComplejaRepository.saveAndFlush(fraseCompleja);

        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();

        // Update the fraseCompleja using partial update
        FraseCompleja partialUpdatedFraseCompleja = new FraseCompleja();
        partialUpdatedFraseCompleja.setId(fraseCompleja.getId());

        partialUpdatedFraseCompleja.frase(UPDATED_FRASE);

        restFraseComplejaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFraseCompleja.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFraseCompleja))
            )
            .andExpect(status().isOk());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
        FraseCompleja testFraseCompleja = fraseComplejaList.get(fraseComplejaList.size() - 1);
        assertThat(testFraseCompleja.getFrase()).isEqualTo(UPDATED_FRASE);
    }

    @Test
    @Transactional
    void patchNonExistingFraseCompleja() throws Exception {
        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();
        fraseCompleja.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraseComplejaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fraseCompleja.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fraseCompleja))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFraseCompleja() throws Exception {
        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();
        fraseCompleja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraseComplejaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fraseCompleja))
            )
            .andExpect(status().isBadRequest());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFraseCompleja() throws Exception {
        int databaseSizeBeforeUpdate = fraseComplejaRepository.findAll().size();
        fraseCompleja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFraseComplejaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fraseCompleja))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FraseCompleja in the database
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFraseCompleja() throws Exception {
        // Initialize the database
        fraseComplejaRepository.saveAndFlush(fraseCompleja);

        int databaseSizeBeforeDelete = fraseComplejaRepository.findAll().size();

        // Delete the fraseCompleja
        restFraseComplejaMockMvc
            .perform(delete(ENTITY_API_URL_ID, fraseCompleja.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FraseCompleja> fraseComplejaList = fraseComplejaRepository.findAll();
        assertThat(fraseComplejaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
