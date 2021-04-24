package frlp.utn.edu.ar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frlp.utn.edu.ar.IntegrationTest;
import frlp.utn.edu.ar.domain.SeccionA;
import frlp.utn.edu.ar.repository.SeccionARepository;
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
 * Integration tests for the {@link SeccionAResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeccionAResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHEQUEADO = false;
    private static final Boolean UPDATED_CHEQUEADO = true;

    private static final String ENTITY_API_URL = "/api/seccion-as";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeccionARepository seccionARepository;

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
        SeccionA seccionA = new SeccionA().descripcion(DEFAULT_DESCRIPCION).chequeado(DEFAULT_CHEQUEADO);
        return seccionA;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeccionA createUpdatedEntity(EntityManager em) {
        SeccionA seccionA = new SeccionA().descripcion(UPDATED_DESCRIPCION).chequeado(UPDATED_CHEQUEADO);
        return seccionA;
    }

    @BeforeEach
    public void initTest() {
        seccionA = createEntity(em);
    }

    @Test
    @Transactional
    void createSeccionA() throws Exception {
        int databaseSizeBeforeCreate = seccionARepository.findAll().size();
        // Create the SeccionA
        restSeccionAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionA)))
            .andExpect(status().isCreated());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeCreate + 1);
        SeccionA testSeccionA = seccionAList.get(seccionAList.size() - 1);
        assertThat(testSeccionA.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSeccionA.getChequeado()).isEqualTo(DEFAULT_CHEQUEADO);
    }

    @Test
    @Transactional
    void createSeccionAWithExistingId() throws Exception {
        // Create the SeccionA with an existing ID
        seccionA.setId(1L);

        int databaseSizeBeforeCreate = seccionARepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionA)))
            .andExpect(status().isBadRequest());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSeccionAS() throws Exception {
        // Initialize the database
        seccionARepository.saveAndFlush(seccionA);

        // Get all the seccionAList
        restSeccionAMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccionA.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].chequeado").value(hasItem(DEFAULT_CHEQUEADO.booleanValue())));
    }

    @Test
    @Transactional
    void getSeccionA() throws Exception {
        // Initialize the database
        seccionARepository.saveAndFlush(seccionA);

        // Get the seccionA
        restSeccionAMockMvc
            .perform(get(ENTITY_API_URL_ID, seccionA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seccionA.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.chequeado").value(DEFAULT_CHEQUEADO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSeccionA() throws Exception {
        // Get the seccionA
        restSeccionAMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeccionA() throws Exception {
        // Initialize the database
        seccionARepository.saveAndFlush(seccionA);

        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();

        // Update the seccionA
        SeccionA updatedSeccionA = seccionARepository.findById(seccionA.getId()).get();
        // Disconnect from session so that the updates on updatedSeccionA are not directly saved in db
        em.detach(updatedSeccionA);
        updatedSeccionA.descripcion(UPDATED_DESCRIPCION).chequeado(UPDATED_CHEQUEADO);

        restSeccionAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSeccionA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSeccionA))
            )
            .andExpect(status().isOk());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
        SeccionA testSeccionA = seccionAList.get(seccionAList.size() - 1);
        assertThat(testSeccionA.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSeccionA.getChequeado()).isEqualTo(UPDATED_CHEQUEADO);
    }

    @Test
    @Transactional
    void putNonExistingSeccionA() throws Exception {
        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();
        seccionA.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seccionA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seccionA))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeccionA() throws Exception {
        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();
        seccionA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seccionA))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeccionA() throws Exception {
        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();
        seccionA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionAMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccionA)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeccionAWithPatch() throws Exception {
        // Initialize the database
        seccionARepository.saveAndFlush(seccionA);

        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();

        // Update the seccionA using partial update
        SeccionA partialUpdatedSeccionA = new SeccionA();
        partialUpdatedSeccionA.setId(seccionA.getId());

        restSeccionAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeccionA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeccionA))
            )
            .andExpect(status().isOk());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
        SeccionA testSeccionA = seccionAList.get(seccionAList.size() - 1);
        assertThat(testSeccionA.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSeccionA.getChequeado()).isEqualTo(DEFAULT_CHEQUEADO);
    }

    @Test
    @Transactional
    void fullUpdateSeccionAWithPatch() throws Exception {
        // Initialize the database
        seccionARepository.saveAndFlush(seccionA);

        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();

        // Update the seccionA using partial update
        SeccionA partialUpdatedSeccionA = new SeccionA();
        partialUpdatedSeccionA.setId(seccionA.getId());

        partialUpdatedSeccionA.descripcion(UPDATED_DESCRIPCION).chequeado(UPDATED_CHEQUEADO);

        restSeccionAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeccionA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeccionA))
            )
            .andExpect(status().isOk());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
        SeccionA testSeccionA = seccionAList.get(seccionAList.size() - 1);
        assertThat(testSeccionA.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSeccionA.getChequeado()).isEqualTo(UPDATED_CHEQUEADO);
    }

    @Test
    @Transactional
    void patchNonExistingSeccionA() throws Exception {
        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();
        seccionA.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seccionA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seccionA))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeccionA() throws Exception {
        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();
        seccionA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seccionA))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeccionA() throws Exception {
        int databaseSizeBeforeUpdate = seccionARepository.findAll().size();
        seccionA.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionAMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seccionA)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeccionA in the database
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeccionA() throws Exception {
        // Initialize the database
        seccionARepository.saveAndFlush(seccionA);

        int databaseSizeBeforeDelete = seccionARepository.findAll().size();

        // Delete the seccionA
        restSeccionAMockMvc
            .perform(delete(ENTITY_API_URL_ID, seccionA.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SeccionA> seccionAList = seccionARepository.findAll();
        assertThat(seccionAList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
