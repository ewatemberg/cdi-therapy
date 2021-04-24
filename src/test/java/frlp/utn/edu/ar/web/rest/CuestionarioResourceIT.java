package frlp.utn.edu.ar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frlp.utn.edu.ar.IntegrationTest;
import frlp.utn.edu.ar.domain.Cuestionario;
import frlp.utn.edu.ar.repository.CuestionarioRepository;
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
 * Integration tests for the {@link CuestionarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CuestionarioResourceIT {

    private static final String ENTITY_API_URL = "/api/cuestionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CuestionarioRepository cuestionarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCuestionarioMockMvc;

    private Cuestionario cuestionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuestionario createEntity(EntityManager em) {
        Cuestionario cuestionario = new Cuestionario();
        return cuestionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuestionario createUpdatedEntity(EntityManager em) {
        Cuestionario cuestionario = new Cuestionario();
        return cuestionario;
    }

    @BeforeEach
    public void initTest() {
        cuestionario = createEntity(em);
    }

    @Test
    @Transactional
    void createCuestionario() throws Exception {
        int databaseSizeBeforeCreate = cuestionarioRepository.findAll().size();
        // Create the Cuestionario
        restCuestionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cuestionario)))
            .andExpect(status().isCreated());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeCreate + 1);
        Cuestionario testCuestionario = cuestionarioList.get(cuestionarioList.size() - 1);
    }

    @Test
    @Transactional
    void createCuestionarioWithExistingId() throws Exception {
        // Create the Cuestionario with an existing ID
        cuestionario.setId(1L);

        int databaseSizeBeforeCreate = cuestionarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuestionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cuestionario)))
            .andExpect(status().isBadRequest());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCuestionarios() throws Exception {
        // Initialize the database
        cuestionarioRepository.saveAndFlush(cuestionario);

        // Get all the cuestionarioList
        restCuestionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuestionario.getId().intValue())));
    }

    @Test
    @Transactional
    void getCuestionario() throws Exception {
        // Initialize the database
        cuestionarioRepository.saveAndFlush(cuestionario);

        // Get the cuestionario
        restCuestionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, cuestionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cuestionario.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCuestionario() throws Exception {
        // Get the cuestionario
        restCuestionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCuestionario() throws Exception {
        // Initialize the database
        cuestionarioRepository.saveAndFlush(cuestionario);

        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();

        // Update the cuestionario
        Cuestionario updatedCuestionario = cuestionarioRepository.findById(cuestionario.getId()).get();
        // Disconnect from session so that the updates on updatedCuestionario are not directly saved in db
        em.detach(updatedCuestionario);

        restCuestionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCuestionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCuestionario))
            )
            .andExpect(status().isOk());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
        Cuestionario testCuestionario = cuestionarioList.get(cuestionarioList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCuestionario() throws Exception {
        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();
        cuestionario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuestionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cuestionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cuestionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCuestionario() throws Exception {
        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();
        cuestionario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuestionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cuestionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCuestionario() throws Exception {
        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();
        cuestionario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuestionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cuestionario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCuestionarioWithPatch() throws Exception {
        // Initialize the database
        cuestionarioRepository.saveAndFlush(cuestionario);

        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();

        // Update the cuestionario using partial update
        Cuestionario partialUpdatedCuestionario = new Cuestionario();
        partialUpdatedCuestionario.setId(cuestionario.getId());

        restCuestionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuestionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCuestionario))
            )
            .andExpect(status().isOk());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
        Cuestionario testCuestionario = cuestionarioList.get(cuestionarioList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCuestionarioWithPatch() throws Exception {
        // Initialize the database
        cuestionarioRepository.saveAndFlush(cuestionario);

        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();

        // Update the cuestionario using partial update
        Cuestionario partialUpdatedCuestionario = new Cuestionario();
        partialUpdatedCuestionario.setId(cuestionario.getId());

        restCuestionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuestionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCuestionario))
            )
            .andExpect(status().isOk());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
        Cuestionario testCuestionario = cuestionarioList.get(cuestionarioList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCuestionario() throws Exception {
        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();
        cuestionario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuestionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cuestionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cuestionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCuestionario() throws Exception {
        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();
        cuestionario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuestionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cuestionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCuestionario() throws Exception {
        int databaseSizeBeforeUpdate = cuestionarioRepository.findAll().size();
        cuestionario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuestionarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cuestionario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cuestionario in the database
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCuestionario() throws Exception {
        // Initialize the database
        cuestionarioRepository.saveAndFlush(cuestionario);

        int databaseSizeBeforeDelete = cuestionarioRepository.findAll().size();

        // Delete the cuestionario
        restCuestionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, cuestionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cuestionario> cuestionarioList = cuestionarioRepository.findAll();
        assertThat(cuestionarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
