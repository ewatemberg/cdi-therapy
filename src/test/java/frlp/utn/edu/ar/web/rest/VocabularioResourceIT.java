package frlp.utn.edu.ar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frlp.utn.edu.ar.IntegrationTest;
import frlp.utn.edu.ar.domain.Vocabulario;
import frlp.utn.edu.ar.domain.enumeration.CategoriaSemantica;
import frlp.utn.edu.ar.repository.VocabularioRepository;
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
 * Integration tests for the {@link VocabularioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VocabularioResourceIT {

    private static final String DEFAULT_PALABRA = "AAAAAAAAAA";
    private static final String UPDATED_PALABRA = "BBBBBBBBBB";

    private static final CategoriaSemantica DEFAULT_CATEGORIA = CategoriaSemantica.SONIDOS;
    private static final CategoriaSemantica UPDATED_CATEGORIA = CategoriaSemantica.ANIMAL;

    private static final String ENTITY_API_URL = "/api/vocabularios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VocabularioRepository vocabularioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVocabularioMockMvc;

    private Vocabulario vocabulario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vocabulario createEntity(EntityManager em) {
        Vocabulario vocabulario = new Vocabulario().palabra(DEFAULT_PALABRA).categoria(DEFAULT_CATEGORIA);
        return vocabulario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vocabulario createUpdatedEntity(EntityManager em) {
        Vocabulario vocabulario = new Vocabulario().palabra(UPDATED_PALABRA).categoria(UPDATED_CATEGORIA);
        return vocabulario;
    }

    @BeforeEach
    public void initTest() {
        vocabulario = createEntity(em);
    }

    @Test
    @Transactional
    void createVocabulario() throws Exception {
        int databaseSizeBeforeCreate = vocabularioRepository.findAll().size();
        // Create the Vocabulario
        restVocabularioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vocabulario)))
            .andExpect(status().isCreated());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeCreate + 1);
        Vocabulario testVocabulario = vocabularioList.get(vocabularioList.size() - 1);
        assertThat(testVocabulario.getPalabra()).isEqualTo(DEFAULT_PALABRA);
        assertThat(testVocabulario.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
    }

    @Test
    @Transactional
    void createVocabularioWithExistingId() throws Exception {
        // Create the Vocabulario with an existing ID
        vocabulario.setId(1L);

        int databaseSizeBeforeCreate = vocabularioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVocabularioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vocabulario)))
            .andExpect(status().isBadRequest());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVocabularios() throws Exception {
        // Initialize the database
        vocabularioRepository.saveAndFlush(vocabulario);

        // Get all the vocabularioList
        restVocabularioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vocabulario.getId().intValue())))
            .andExpect(jsonPath("$.[*].palabra").value(hasItem(DEFAULT_PALABRA)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())));
    }

    @Test
    @Transactional
    void getVocabulario() throws Exception {
        // Initialize the database
        vocabularioRepository.saveAndFlush(vocabulario);

        // Get the vocabulario
        restVocabularioMockMvc
            .perform(get(ENTITY_API_URL_ID, vocabulario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vocabulario.getId().intValue()))
            .andExpect(jsonPath("$.palabra").value(DEFAULT_PALABRA))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingVocabulario() throws Exception {
        // Get the vocabulario
        restVocabularioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVocabulario() throws Exception {
        // Initialize the database
        vocabularioRepository.saveAndFlush(vocabulario);

        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();

        // Update the vocabulario
        Vocabulario updatedVocabulario = vocabularioRepository.findById(vocabulario.getId()).get();
        // Disconnect from session so that the updates on updatedVocabulario are not directly saved in db
        em.detach(updatedVocabulario);
        updatedVocabulario.palabra(UPDATED_PALABRA).categoria(UPDATED_CATEGORIA);

        restVocabularioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVocabulario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVocabulario))
            )
            .andExpect(status().isOk());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeUpdate);
        Vocabulario testVocabulario = vocabularioList.get(vocabularioList.size() - 1);
        assertThat(testVocabulario.getPalabra()).isEqualTo(UPDATED_PALABRA);
        assertThat(testVocabulario.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    void putNonExistingVocabulario() throws Exception {
        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();
        vocabulario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVocabularioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vocabulario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vocabulario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVocabulario() throws Exception {
        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();
        vocabulario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVocabularioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vocabulario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVocabulario() throws Exception {
        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();
        vocabulario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVocabularioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vocabulario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVocabularioWithPatch() throws Exception {
        // Initialize the database
        vocabularioRepository.saveAndFlush(vocabulario);

        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();

        // Update the vocabulario using partial update
        Vocabulario partialUpdatedVocabulario = new Vocabulario();
        partialUpdatedVocabulario.setId(vocabulario.getId());

        partialUpdatedVocabulario.categoria(UPDATED_CATEGORIA);

        restVocabularioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVocabulario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVocabulario))
            )
            .andExpect(status().isOk());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeUpdate);
        Vocabulario testVocabulario = vocabularioList.get(vocabularioList.size() - 1);
        assertThat(testVocabulario.getPalabra()).isEqualTo(DEFAULT_PALABRA);
        assertThat(testVocabulario.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    void fullUpdateVocabularioWithPatch() throws Exception {
        // Initialize the database
        vocabularioRepository.saveAndFlush(vocabulario);

        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();

        // Update the vocabulario using partial update
        Vocabulario partialUpdatedVocabulario = new Vocabulario();
        partialUpdatedVocabulario.setId(vocabulario.getId());

        partialUpdatedVocabulario.palabra(UPDATED_PALABRA).categoria(UPDATED_CATEGORIA);

        restVocabularioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVocabulario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVocabulario))
            )
            .andExpect(status().isOk());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeUpdate);
        Vocabulario testVocabulario = vocabularioList.get(vocabularioList.size() - 1);
        assertThat(testVocabulario.getPalabra()).isEqualTo(UPDATED_PALABRA);
        assertThat(testVocabulario.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    void patchNonExistingVocabulario() throws Exception {
        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();
        vocabulario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVocabularioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vocabulario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vocabulario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVocabulario() throws Exception {
        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();
        vocabulario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVocabularioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vocabulario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVocabulario() throws Exception {
        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();
        vocabulario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVocabularioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vocabulario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVocabulario() throws Exception {
        // Initialize the database
        vocabularioRepository.saveAndFlush(vocabulario);

        int databaseSizeBeforeDelete = vocabularioRepository.findAll().size();

        // Delete the vocabulario
        restVocabularioMockMvc
            .perform(delete(ENTITY_API_URL_ID, vocabulario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
