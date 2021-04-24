package frlp.utn.edu.ar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frlp.utn.edu.ar.IntegrationTest;
import frlp.utn.edu.ar.domain.FormaVerbal;
import frlp.utn.edu.ar.repository.FormaVerbalRepository;
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
 * Integration tests for the {@link FormaVerbalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormaVerbalResourceIT {

    private static final String DEFAULT_FORMA = "AAAAAAAAAA";
    private static final String UPDATED_FORMA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/forma-verbals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormaVerbalRepository formaVerbalRepository;

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
        FormaVerbal formaVerbal = new FormaVerbal().forma(DEFAULT_FORMA);
        return formaVerbal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaVerbal createUpdatedEntity(EntityManager em) {
        FormaVerbal formaVerbal = new FormaVerbal().forma(UPDATED_FORMA);
        return formaVerbal;
    }

    @BeforeEach
    public void initTest() {
        formaVerbal = createEntity(em);
    }

    @Test
    @Transactional
    void createFormaVerbal() throws Exception {
        int databaseSizeBeforeCreate = formaVerbalRepository.findAll().size();
        // Create the FormaVerbal
        restFormaVerbalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formaVerbal)))
            .andExpect(status().isCreated());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeCreate + 1);
        FormaVerbal testFormaVerbal = formaVerbalList.get(formaVerbalList.size() - 1);
        assertThat(testFormaVerbal.getForma()).isEqualTo(DEFAULT_FORMA);
    }

    @Test
    @Transactional
    void createFormaVerbalWithExistingId() throws Exception {
        // Create the FormaVerbal with an existing ID
        formaVerbal.setId(1L);

        int databaseSizeBeforeCreate = formaVerbalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormaVerbalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formaVerbal)))
            .andExpect(status().isBadRequest());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormaVerbals() throws Exception {
        // Initialize the database
        formaVerbalRepository.saveAndFlush(formaVerbal);

        // Get all the formaVerbalList
        restFormaVerbalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaVerbal.getId().intValue())))
            .andExpect(jsonPath("$.[*].forma").value(hasItem(DEFAULT_FORMA)));
    }

    @Test
    @Transactional
    void getFormaVerbal() throws Exception {
        // Initialize the database
        formaVerbalRepository.saveAndFlush(formaVerbal);

        // Get the formaVerbal
        restFormaVerbalMockMvc
            .perform(get(ENTITY_API_URL_ID, formaVerbal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formaVerbal.getId().intValue()))
            .andExpect(jsonPath("$.forma").value(DEFAULT_FORMA));
    }

    @Test
    @Transactional
    void getNonExistingFormaVerbal() throws Exception {
        // Get the formaVerbal
        restFormaVerbalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFormaVerbal() throws Exception {
        // Initialize the database
        formaVerbalRepository.saveAndFlush(formaVerbal);

        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();

        // Update the formaVerbal
        FormaVerbal updatedFormaVerbal = formaVerbalRepository.findById(formaVerbal.getId()).get();
        // Disconnect from session so that the updates on updatedFormaVerbal are not directly saved in db
        em.detach(updatedFormaVerbal);
        updatedFormaVerbal.forma(UPDATED_FORMA);

        restFormaVerbalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormaVerbal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFormaVerbal))
            )
            .andExpect(status().isOk());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
        FormaVerbal testFormaVerbal = formaVerbalList.get(formaVerbalList.size() - 1);
        assertThat(testFormaVerbal.getForma()).isEqualTo(UPDATED_FORMA);
    }

    @Test
    @Transactional
    void putNonExistingFormaVerbal() throws Exception {
        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();
        formaVerbal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaVerbalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formaVerbal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formaVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormaVerbal() throws Exception {
        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();
        formaVerbal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaVerbalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formaVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormaVerbal() throws Exception {
        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();
        formaVerbal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaVerbalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formaVerbal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormaVerbalWithPatch() throws Exception {
        // Initialize the database
        formaVerbalRepository.saveAndFlush(formaVerbal);

        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();

        // Update the formaVerbal using partial update
        FormaVerbal partialUpdatedFormaVerbal = new FormaVerbal();
        partialUpdatedFormaVerbal.setId(formaVerbal.getId());

        restFormaVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormaVerbal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormaVerbal))
            )
            .andExpect(status().isOk());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
        FormaVerbal testFormaVerbal = formaVerbalList.get(formaVerbalList.size() - 1);
        assertThat(testFormaVerbal.getForma()).isEqualTo(DEFAULT_FORMA);
    }

    @Test
    @Transactional
    void fullUpdateFormaVerbalWithPatch() throws Exception {
        // Initialize the database
        formaVerbalRepository.saveAndFlush(formaVerbal);

        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();

        // Update the formaVerbal using partial update
        FormaVerbal partialUpdatedFormaVerbal = new FormaVerbal();
        partialUpdatedFormaVerbal.setId(formaVerbal.getId());

        partialUpdatedFormaVerbal.forma(UPDATED_FORMA);

        restFormaVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormaVerbal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormaVerbal))
            )
            .andExpect(status().isOk());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
        FormaVerbal testFormaVerbal = formaVerbalList.get(formaVerbalList.size() - 1);
        assertThat(testFormaVerbal.getForma()).isEqualTo(UPDATED_FORMA);
    }

    @Test
    @Transactional
    void patchNonExistingFormaVerbal() throws Exception {
        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();
        formaVerbal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formaVerbal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formaVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormaVerbal() throws Exception {
        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();
        formaVerbal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formaVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormaVerbal() throws Exception {
        int databaseSizeBeforeUpdate = formaVerbalRepository.findAll().size();
        formaVerbal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(formaVerbal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormaVerbal in the database
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormaVerbal() throws Exception {
        // Initialize the database
        formaVerbalRepository.saveAndFlush(formaVerbal);

        int databaseSizeBeforeDelete = formaVerbalRepository.findAll().size();

        // Delete the formaVerbal
        restFormaVerbalMockMvc
            .perform(delete(ENTITY_API_URL_ID, formaVerbal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormaVerbal> formaVerbalList = formaVerbalRepository.findAll();
        assertThat(formaVerbalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
