package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.CdiApp;
import frlp.utn.edu.ar.domain.Vocabulario;
import frlp.utn.edu.ar.repository.VocabularioRepository;
import frlp.utn.edu.ar.service.VocabularioService;

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

import frlp.utn.edu.ar.domain.enumeration.CategoriaSemantica;
/**
 * Integration tests for the {@link VocabularioResource} REST controller.
 */
@SpringBootTest(classes = CdiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VocabularioResourceIT {

    private static final String DEFAULT_PALABRA = "AAAAAAAAAA";
    private static final String UPDATED_PALABRA = "BBBBBBBBBB";

    private static final CategoriaSemantica DEFAULT_CATEGORIA = CategoriaSemantica.SONIDOS;
    private static final CategoriaSemantica UPDATED_CATEGORIA = CategoriaSemantica.ANIMAL;

    @Autowired
    private VocabularioRepository vocabularioRepository;

    @Autowired
    private VocabularioService vocabularioService;

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
        Vocabulario vocabulario = new Vocabulario()
            .palabra(DEFAULT_PALABRA)
            .categoria(DEFAULT_CATEGORIA);
        return vocabulario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vocabulario createUpdatedEntity(EntityManager em) {
        Vocabulario vocabulario = new Vocabulario()
            .palabra(UPDATED_PALABRA)
            .categoria(UPDATED_CATEGORIA);
        return vocabulario;
    }

    @BeforeEach
    public void initTest() {
        vocabulario = createEntity(em);
    }

    @Test
    @Transactional
    public void createVocabulario() throws Exception {
        int databaseSizeBeforeCreate = vocabularioRepository.findAll().size();
        // Create the Vocabulario
        restVocabularioMockMvc.perform(post("/api/vocabularios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vocabulario)))
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
    public void createVocabularioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vocabularioRepository.findAll().size();

        // Create the Vocabulario with an existing ID
        vocabulario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVocabularioMockMvc.perform(post("/api/vocabularios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vocabulario)))
            .andExpect(status().isBadRequest());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVocabularios() throws Exception {
        // Initialize the database
        vocabularioRepository.saveAndFlush(vocabulario);

        // Get all the vocabularioList
        restVocabularioMockMvc.perform(get("/api/vocabularios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vocabulario.getId().intValue())))
            .andExpect(jsonPath("$.[*].palabra").value(hasItem(DEFAULT_PALABRA)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())));
    }
    
    @Test
    @Transactional
    public void getVocabulario() throws Exception {
        // Initialize the database
        vocabularioRepository.saveAndFlush(vocabulario);

        // Get the vocabulario
        restVocabularioMockMvc.perform(get("/api/vocabularios/{id}", vocabulario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vocabulario.getId().intValue()))
            .andExpect(jsonPath("$.palabra").value(DEFAULT_PALABRA))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVocabulario() throws Exception {
        // Get the vocabulario
        restVocabularioMockMvc.perform(get("/api/vocabularios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVocabulario() throws Exception {
        // Initialize the database
        vocabularioService.save(vocabulario);

        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();

        // Update the vocabulario
        Vocabulario updatedVocabulario = vocabularioRepository.findById(vocabulario.getId()).get();
        // Disconnect from session so that the updates on updatedVocabulario are not directly saved in db
        em.detach(updatedVocabulario);
        updatedVocabulario
            .palabra(UPDATED_PALABRA)
            .categoria(UPDATED_CATEGORIA);

        restVocabularioMockMvc.perform(put("/api/vocabularios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVocabulario)))
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
    public void updateNonExistingVocabulario() throws Exception {
        int databaseSizeBeforeUpdate = vocabularioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVocabularioMockMvc.perform(put("/api/vocabularios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vocabulario)))
            .andExpect(status().isBadRequest());

        // Validate the Vocabulario in the database
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVocabulario() throws Exception {
        // Initialize the database
        vocabularioService.save(vocabulario);

        int databaseSizeBeforeDelete = vocabularioRepository.findAll().size();

        // Delete the vocabulario
        restVocabularioMockMvc.perform(delete("/api/vocabularios/{id}", vocabulario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vocabulario> vocabularioList = vocabularioRepository.findAll();
        assertThat(vocabularioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
