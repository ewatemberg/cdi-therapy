package frlp.utn.edu.ar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frlp.utn.edu.ar.IntegrationTest;
import frlp.utn.edu.ar.domain.SeccionB;
import frlp.utn.edu.ar.repository.SeccionBRepository;
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
 * Integration tests for the {@link SeccionBResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeccionBResourceIT {

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    private static final String ENTITY_API_URL = "/api/seccion-bs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeccionBRepository seccionBRepository;

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
        SeccionB seccionB = new SeccionB().valor(DEFAULT_VALOR);
        return seccionB;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionB createUpdatedEntity(EntityManager em) {
        SeccionB seccionB = new SeccionB().valor(UPDATED_VALOR);
        return seccionB;
    }

    @BeforeEach
    public void initTest() {
        seccionB = createEntity(em);
    }

    @Test
    @Transactional
    void createSeccionB() throws Exception {
        int databaseSizeBeforeCreate = seccionBRepository.findAll().size();
        // Create the SeccionB
        restSeccionBMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionB)))
            .andExpect(status().isCreated());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeCreate + 1);
        SeccionB testSeccionB = seccionBList.get(seccionBList.size() - 1);
        assertThat(testSeccionB.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createSeccionBWithExistingId() throws Exception {
        // Create the SeccionB with an existing ID
        seccionB.setId(1L);

        int databaseSizeBeforeCreate = seccionBRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionBMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionB)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSeccionBS() throws Exception {
        // Initialize the database
        seccionBRepository.saveAndFlush(seccionB);

        // Get all the seccionBList
        restSeccionBMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccionB.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    void getSeccionB() throws Exception {
        // Initialize the database
        seccionBRepository.saveAndFlush(seccionB);

        // Get the seccionB
        restSeccionBMockMvc
            .perform(get(ENTITY_API_URL_ID, seccionB.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seccionB.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    void getNonExistingSeccionB() throws Exception {
        // Get the seccionB
        restSeccionBMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeccionB() throws Exception {
        // Initialize the database
        seccionBRepository.saveAndFlush(seccionB);

        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();

        // Update the seccionB
        SeccionB updatedSeccionB = seccionBRepository.findById(seccionB.getId()).get();
        // Disconnect from session so that the updates on updatedSeccionB are not directly saved in db
        em.detach(updatedSeccionB);
        updatedSeccionB.valor(UPDATED_VALOR);

        restSeccionBMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSeccionB.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSeccionB))
            )
            .andExpect(status().isOk());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
        SeccionB testSeccionB = seccionBList.get(seccionBList.size() - 1);
        assertThat(testSeccionB.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingSeccionB() throws Exception {
        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();
        seccionB.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionBMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seccionB.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seccionB))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeccionB() throws Exception {
        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();
        seccionB.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionBMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seccionB))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeccionB() throws Exception {
        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();
        seccionB.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionBMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionB)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeccionBWithPatch() throws Exception {
        // Initialize the database
        seccionBRepository.saveAndFlush(seccionB);

        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();

        // Update the seccionB using partial update
        SeccionB partialUpdatedSeccionB = new SeccionB();
        partialUpdatedSeccionB.setId(seccionB.getId());

        restSeccionBMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeccionB.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeccionB))
            )
            .andExpect(status().isOk());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
        SeccionB testSeccionB = seccionBList.get(seccionBList.size() - 1);
        assertThat(testSeccionB.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateSeccionBWithPatch() throws Exception {
        // Initialize the database
        seccionBRepository.saveAndFlush(seccionB);

        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();

        // Update the seccionB using partial update
        SeccionB partialUpdatedSeccionB = new SeccionB();
        partialUpdatedSeccionB.setId(seccionB.getId());

        partialUpdatedSeccionB.valor(UPDATED_VALOR);

        restSeccionBMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeccionB.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeccionB))
            )
            .andExpect(status().isOk());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
        SeccionB testSeccionB = seccionBList.get(seccionBList.size() - 1);
        assertThat(testSeccionB.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingSeccionB() throws Exception {
        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();
        seccionB.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionBMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seccionB.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seccionB))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeccionB() throws Exception {
        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();
        seccionB.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionBMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seccionB))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeccionB() throws Exception {
        int databaseSizeBeforeUpdate = seccionBRepository.findAll().size();
        seccionB.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionBMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seccionB)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeccionB in the database
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeccionB() throws Exception {
        // Initialize the database
        seccionBRepository.saveAndFlush(seccionB);

        int databaseSizeBeforeDelete = seccionBRepository.findAll().size();

        // Delete the seccionB
        restSeccionBMockMvc
            .perform(delete(ENTITY_API_URL_ID, seccionB.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeccionB> seccionBList = seccionBRepository.findAll();
        assertThat(seccionBList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
