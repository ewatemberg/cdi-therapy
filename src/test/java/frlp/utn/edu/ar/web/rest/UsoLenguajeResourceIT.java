package frlp.utn.edu.ar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frlp.utn.edu.ar.IntegrationTest;
import frlp.utn.edu.ar.domain.UsoLenguaje;
import frlp.utn.edu.ar.repository.UsoLenguajeRepository;
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
 * Integration tests for the {@link UsoLenguajeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UsoLenguajeResourceIT {

    private static final String DEFAULT_PREGUNTA = "AAAAAAAAAA";
    private static final String UPDATED_PREGUNTA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/uso-lenguajes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsoLenguajeRepository usoLenguajeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsoLenguajeMockMvc;

    private UsoLenguaje usoLenguaje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsoLenguaje createEntity(EntityManager em) {
        UsoLenguaje usoLenguaje = new UsoLenguaje().pregunta(DEFAULT_PREGUNTA);
        return usoLenguaje;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsoLenguaje createUpdatedEntity(EntityManager em) {
        UsoLenguaje usoLenguaje = new UsoLenguaje().pregunta(UPDATED_PREGUNTA);
        return usoLenguaje;
    }

    @BeforeEach
    public void initTest() {
        usoLenguaje = createEntity(em);
    }

    @Test
    @Transactional
    void createUsoLenguaje() throws Exception {
        int databaseSizeBeforeCreate = usoLenguajeRepository.findAll().size();
        // Create the UsoLenguaje
        restUsoLenguajeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usoLenguaje)))
            .andExpect(status().isCreated());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeCreate + 1);
        UsoLenguaje testUsoLenguaje = usoLenguajeList.get(usoLenguajeList.size() - 1);
        assertThat(testUsoLenguaje.getPregunta()).isEqualTo(DEFAULT_PREGUNTA);
    }

    @Test
    @Transactional
    void createUsoLenguajeWithExistingId() throws Exception {
        // Create the UsoLenguaje with an existing ID
        usoLenguaje.setId(1L);

        int databaseSizeBeforeCreate = usoLenguajeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsoLenguajeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usoLenguaje)))
            .andExpect(status().isBadRequest());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUsoLenguajes() throws Exception {
        // Initialize the database
        usoLenguajeRepository.saveAndFlush(usoLenguaje);

        // Get all the usoLenguajeList
        restUsoLenguajeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usoLenguaje.getId().intValue())))
            .andExpect(jsonPath("$.[*].pregunta").value(hasItem(DEFAULT_PREGUNTA)));
    }

    @Test
    @Transactional
    void getUsoLenguaje() throws Exception {
        // Initialize the database
        usoLenguajeRepository.saveAndFlush(usoLenguaje);

        // Get the usoLenguaje
        restUsoLenguajeMockMvc
            .perform(get(ENTITY_API_URL_ID, usoLenguaje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usoLenguaje.getId().intValue()))
            .andExpect(jsonPath("$.pregunta").value(DEFAULT_PREGUNTA));
    }

    @Test
    @Transactional
    void getNonExistingUsoLenguaje() throws Exception {
        // Get the usoLenguaje
        restUsoLenguajeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUsoLenguaje() throws Exception {
        // Initialize the database
        usoLenguajeRepository.saveAndFlush(usoLenguaje);

        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();

        // Update the usoLenguaje
        UsoLenguaje updatedUsoLenguaje = usoLenguajeRepository.findById(usoLenguaje.getId()).get();
        // Disconnect from session so that the updates on updatedUsoLenguaje are not directly saved in db
        em.detach(updatedUsoLenguaje);
        updatedUsoLenguaje.pregunta(UPDATED_PREGUNTA);

        restUsoLenguajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUsoLenguaje.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUsoLenguaje))
            )
            .andExpect(status().isOk());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
        UsoLenguaje testUsoLenguaje = usoLenguajeList.get(usoLenguajeList.size() - 1);
        assertThat(testUsoLenguaje.getPregunta()).isEqualTo(UPDATED_PREGUNTA);
    }

    @Test
    @Transactional
    void putNonExistingUsoLenguaje() throws Exception {
        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();
        usoLenguaje.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsoLenguajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usoLenguaje.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usoLenguaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsoLenguaje() throws Exception {
        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();
        usoLenguaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsoLenguajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usoLenguaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsoLenguaje() throws Exception {
        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();
        usoLenguaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsoLenguajeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usoLenguaje)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsoLenguajeWithPatch() throws Exception {
        // Initialize the database
        usoLenguajeRepository.saveAndFlush(usoLenguaje);

        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();

        // Update the usoLenguaje using partial update
        UsoLenguaje partialUpdatedUsoLenguaje = new UsoLenguaje();
        partialUpdatedUsoLenguaje.setId(usoLenguaje.getId());

        restUsoLenguajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsoLenguaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsoLenguaje))
            )
            .andExpect(status().isOk());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
        UsoLenguaje testUsoLenguaje = usoLenguajeList.get(usoLenguajeList.size() - 1);
        assertThat(testUsoLenguaje.getPregunta()).isEqualTo(DEFAULT_PREGUNTA);
    }

    @Test
    @Transactional
    void fullUpdateUsoLenguajeWithPatch() throws Exception {
        // Initialize the database
        usoLenguajeRepository.saveAndFlush(usoLenguaje);

        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();

        // Update the usoLenguaje using partial update
        UsoLenguaje partialUpdatedUsoLenguaje = new UsoLenguaje();
        partialUpdatedUsoLenguaje.setId(usoLenguaje.getId());

        partialUpdatedUsoLenguaje.pregunta(UPDATED_PREGUNTA);

        restUsoLenguajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsoLenguaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsoLenguaje))
            )
            .andExpect(status().isOk());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
        UsoLenguaje testUsoLenguaje = usoLenguajeList.get(usoLenguajeList.size() - 1);
        assertThat(testUsoLenguaje.getPregunta()).isEqualTo(UPDATED_PREGUNTA);
    }

    @Test
    @Transactional
    void patchNonExistingUsoLenguaje() throws Exception {
        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();
        usoLenguaje.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsoLenguajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usoLenguaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usoLenguaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsoLenguaje() throws Exception {
        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();
        usoLenguaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsoLenguajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usoLenguaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsoLenguaje() throws Exception {
        int databaseSizeBeforeUpdate = usoLenguajeRepository.findAll().size();
        usoLenguaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsoLenguajeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(usoLenguaje))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsoLenguaje in the database
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsoLenguaje() throws Exception {
        // Initialize the database
        usoLenguajeRepository.saveAndFlush(usoLenguaje);

        int databaseSizeBeforeDelete = usoLenguajeRepository.findAll().size();

        // Delete the usoLenguaje
        restUsoLenguajeMockMvc
            .perform(delete(ENTITY_API_URL_ID, usoLenguaje.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsoLenguaje> usoLenguajeList = usoLenguajeRepository.findAll();
        assertThat(usoLenguajeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
