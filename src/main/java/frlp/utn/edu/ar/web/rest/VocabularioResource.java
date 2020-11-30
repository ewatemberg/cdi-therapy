package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.Vocabulario;
import frlp.utn.edu.ar.service.VocabularioService;
import frlp.utn.edu.ar.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link frlp.utn.edu.ar.domain.Vocabulario}.
 */
@RestController
@RequestMapping("/api")
public class VocabularioResource {

    private final Logger log = LoggerFactory.getLogger(VocabularioResource.class);

    private static final String ENTITY_NAME = "vocabulario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VocabularioService vocabularioService;

    public VocabularioResource(VocabularioService vocabularioService) {
        this.vocabularioService = vocabularioService;
    }

    /**
     * {@code POST  /vocabularios} : Create a new vocabulario.
     *
     * @param vocabulario the vocabulario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vocabulario, or with status {@code 400 (Bad Request)} if the vocabulario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vocabularios")
    public ResponseEntity<Vocabulario> createVocabulario(@RequestBody Vocabulario vocabulario) throws URISyntaxException {
        log.debug("REST request to save Vocabulario : {}", vocabulario);
        if (vocabulario.getId() != null) {
            throw new BadRequestAlertException("A new vocabulario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vocabulario result = vocabularioService.save(vocabulario);
        return ResponseEntity.created(new URI("/api/vocabularios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vocabularios} : Updates an existing vocabulario.
     *
     * @param vocabulario the vocabulario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vocabulario,
     * or with status {@code 400 (Bad Request)} if the vocabulario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vocabulario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vocabularios")
    public ResponseEntity<Vocabulario> updateVocabulario(@RequestBody Vocabulario vocabulario) throws URISyntaxException {
        log.debug("REST request to update Vocabulario : {}", vocabulario);
        if (vocabulario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vocabulario result = vocabularioService.save(vocabulario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vocabulario.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vocabularios} : get all the vocabularios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vocabularios in body.
     */
    @GetMapping("/vocabularios")
    public List<Vocabulario> getAllVocabularios() {
        log.debug("REST request to get all Vocabularios");
        return vocabularioService.findAll();
    }

    /**
     * {@code GET  /vocabularios/:id} : get the "id" vocabulario.
     *
     * @param id the id of the vocabulario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vocabulario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vocabularios/{id}")
    public ResponseEntity<Vocabulario> getVocabulario(@PathVariable Long id) {
        log.debug("REST request to get Vocabulario : {}", id);
        Optional<Vocabulario> vocabulario = vocabularioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vocabulario);
    }

    /**
     * {@code DELETE  /vocabularios/:id} : delete the "id" vocabulario.
     *
     * @param id the id of the vocabulario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vocabularios/{id}")
    public ResponseEntity<Void> deleteVocabulario(@PathVariable Long id) {
        log.debug("REST request to delete Vocabulario : {}", id);
        vocabularioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
