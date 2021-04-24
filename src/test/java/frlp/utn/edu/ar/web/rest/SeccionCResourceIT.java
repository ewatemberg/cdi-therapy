package frlp.utn.edu.ar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frlp.utn.edu.ar.IntegrationTest;
import frlp.utn.edu.ar.domain.SeccionC;
import frlp.utn.edu.ar.repository.SeccionCRepository;
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
 * Integration tests for the {@link SeccionCResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeccionCResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    private static final String ENTITY_API_URL = "/api/seccion-cs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeccionCRepository seccionCRepository;

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
        SeccionC seccionC = new SeccionC().descripcion(DEFAULT_DESCRIPCION).valor(DEFAULT_VALOR);
        return seccionC;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionC createUpdatedEntity(EntityManager em) {
        SeccionC seccionC = new SeccionC().descripcion(UPDATED_DESCRIPCION).valor(UPDATED_VALOR);
        return seccionC;
    }

    @BeforeEach
    public void initTest() {
        seccionC = createEntity(em);
    }

    @Test
    @Transactional
    void createSeccionC() throws Exception {
        int databaseSizeBeforeCreate = seccionCRepository.findAll().size();
        // Create the SeccionC
        restSeccionCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionC)))
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
    void createSeccionCWithExistingId() throws Exception {
        // Create the SeccionC with an existing ID
        seccionC.setId(1L);

        int databaseSizeBeforeCreate = seccionCRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionCMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionC)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSeccionCS() throws Exception {
        // Initialize the database
        seccionCRepository.saveAndFlush(seccionC);

        // Get all the seccionCList
        restSeccionCMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccionC.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    void getSeccionC() throws Exception {
        // Initialize the database
        seccionCRepository.saveAndFlush(seccionC);

        // Get the seccionC
        restSeccionCMockMvc
            .perform(get(ENTITY_API_URL_ID, seccionC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seccionC.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    void getNonExistingSeccionC() throws Exception {
        // Get the seccionC
        restSeccionCMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeccionC() throws Exception {
        // Initialize the database
        seccionCRepository.saveAndFlush(seccionC);

        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();

        // Update the seccionC
        SeccionC updatedSeccionC = seccionCRepository.findById(seccionC.getId()).get();
        // Disconnect from session so that the updates on updatedSeccionC are not directly saved in db
        em.detach(updatedSeccionC);
        updatedSeccionC.descripcion(UPDATED_DESCRIPCION).valor(UPDATED_VALOR);

        restSeccionCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSeccionC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSeccionC))
            )
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
    void putNonExistingSeccionC() throws Exception {
        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();
        seccionC.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seccionC.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seccionC))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeccionC() throws Exception {
        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();
        seccionC.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionCMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seccionC))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeccionC() throws Exception {
        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();
        seccionC.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionCMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeccionCWithPatch() throws Exception {
        // Initialize the database
        seccionCRepository.saveAndFlush(seccionC);

        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();

        // Update the seccionC using partial update
        SeccionC partialUpdatedSeccionC = new SeccionC();
        partialUpdatedSeccionC.setId(seccionC.getId());

        partialUpdatedSeccionC.descripcion(UPDATED_DESCRIPCION);

        restSeccionCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeccionC.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeccionC))
            )
            .andExpect(status().isOk());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeUpdate);
        SeccionC testSeccionC = seccionCList.get(seccionCList.size() - 1);
        assertThat(testSeccionC.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSeccionC.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateSeccionCWithPatch() throws Exception {
        // Initialize the database
        seccionCRepository.saveAndFlush(seccionC);

        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();

        // Update the seccionC using partial update
        SeccionC partialUpdatedSeccionC = new SeccionC();
        partialUpdatedSeccionC.setId(seccionC.getId());

        partialUpdatedSeccionC.descripcion(UPDATED_DESCRIPCION).valor(UPDATED_VALOR);

        restSeccionCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeccionC.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeccionC))
            )
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
    void patchNonExistingSeccionC() throws Exception {
        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();
        seccionC.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seccionC.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seccionC))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeccionC() throws Exception {
        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();
        seccionC.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionCMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seccionC))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeccionC() throws Exception {
        int databaseSizeBeforeUpdate = seccionCRepository.findAll().size();
        seccionC.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionCMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seccionC)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeccionC in the database
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeccionC() throws Exception {
        // Initialize the database
        seccionCRepository.saveAndFlush(seccionC);

        int databaseSizeBeforeDelete = seccionCRepository.findAll().size();

        // Delete the seccionC
        restSeccionCMockMvc
            .perform(delete(ENTITY_API_URL_ID, seccionC.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeccionC> seccionCList = seccionCRepository.findAll();
        assertThat(seccionCList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
