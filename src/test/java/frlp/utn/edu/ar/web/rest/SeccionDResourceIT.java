package frlp.utn.edu.ar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frlp.utn.edu.ar.IntegrationTest;
import frlp.utn.edu.ar.domain.SeccionD;
import frlp.utn.edu.ar.repository.SeccionDRepository;
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
 * Integration tests for the {@link SeccionDResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeccionDResourceIT {

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    private static final String ENTITY_API_URL = "/api/seccion-ds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeccionDRepository seccionDRepository;

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
        SeccionD seccionD = new SeccionD().valor(DEFAULT_VALOR);
        return seccionD;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionD createUpdatedEntity(EntityManager em) {
        SeccionD seccionD = new SeccionD().valor(UPDATED_VALOR);
        return seccionD;
    }

    @BeforeEach
    public void initTest() {
        seccionD = createEntity(em);
    }

    @Test
    @Transactional
    void createSeccionD() throws Exception {
        int databaseSizeBeforeCreate = seccionDRepository.findAll().size();
        // Create the SeccionD
        restSeccionDMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionD)))
            .andExpect(status().isCreated());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeCreate + 1);
        SeccionD testSeccionD = seccionDList.get(seccionDList.size() - 1);
        assertThat(testSeccionD.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createSeccionDWithExistingId() throws Exception {
        // Create the SeccionD with an existing ID
        seccionD.setId(1L);

        int databaseSizeBeforeCreate = seccionDRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionDMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionD)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSeccionDS() throws Exception {
        // Initialize the database
        seccionDRepository.saveAndFlush(seccionD);

        // Get all the seccionDList
        restSeccionDMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccionD.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    void getSeccionD() throws Exception {
        // Initialize the database
        seccionDRepository.saveAndFlush(seccionD);

        // Get the seccionD
        restSeccionDMockMvc
            .perform(get(ENTITY_API_URL_ID, seccionD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seccionD.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    void getNonExistingSeccionD() throws Exception {
        // Get the seccionD
        restSeccionDMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeccionD() throws Exception {
        // Initialize the database
        seccionDRepository.saveAndFlush(seccionD);

        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();

        // Update the seccionD
        SeccionD updatedSeccionD = seccionDRepository.findById(seccionD.getId()).get();
        // Disconnect from session so that the updates on updatedSeccionD are not directly saved in db
        em.detach(updatedSeccionD);
        updatedSeccionD.valor(UPDATED_VALOR);

        restSeccionDMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSeccionD.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSeccionD))
            )
            .andExpect(status().isOk());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
        SeccionD testSeccionD = seccionDList.get(seccionDList.size() - 1);
        assertThat(testSeccionD.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingSeccionD() throws Exception {
        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();
        seccionD.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionDMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seccionD.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seccionD))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeccionD() throws Exception {
        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();
        seccionD.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionDMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seccionD))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeccionD() throws Exception {
        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();
        seccionD.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionDMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionD)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeccionDWithPatch() throws Exception {
        // Initialize the database
        seccionDRepository.saveAndFlush(seccionD);

        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();

        // Update the seccionD using partial update
        SeccionD partialUpdatedSeccionD = new SeccionD();
        partialUpdatedSeccionD.setId(seccionD.getId());

        partialUpdatedSeccionD.valor(UPDATED_VALOR);

        restSeccionDMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeccionD.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeccionD))
            )
            .andExpect(status().isOk());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
        SeccionD testSeccionD = seccionDList.get(seccionDList.size() - 1);
        assertThat(testSeccionD.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateSeccionDWithPatch() throws Exception {
        // Initialize the database
        seccionDRepository.saveAndFlush(seccionD);

        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();

        // Update the seccionD using partial update
        SeccionD partialUpdatedSeccionD = new SeccionD();
        partialUpdatedSeccionD.setId(seccionD.getId());

        partialUpdatedSeccionD.valor(UPDATED_VALOR);

        restSeccionDMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeccionD.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeccionD))
            )
            .andExpect(status().isOk());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
        SeccionD testSeccionD = seccionDList.get(seccionDList.size() - 1);
        assertThat(testSeccionD.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingSeccionD() throws Exception {
        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();
        seccionD.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionDMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seccionD.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seccionD))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeccionD() throws Exception {
        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();
        seccionD.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionDMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seccionD))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeccionD() throws Exception {
        int databaseSizeBeforeUpdate = seccionDRepository.findAll().size();
        seccionD.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionDMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seccionD)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeccionD in the database
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeccionD() throws Exception {
        // Initialize the database
        seccionDRepository.saveAndFlush(seccionD);

        int databaseSizeBeforeDelete = seccionDRepository.findAll().size();

        // Delete the seccionD
        restSeccionDMockMvc
            .perform(delete(ENTITY_API_URL_ID, seccionD.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeccionD> seccionDList = seccionDRepository.findAll();
        assertThat(seccionDList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
