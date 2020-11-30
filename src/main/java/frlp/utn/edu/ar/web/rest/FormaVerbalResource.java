package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.FormaVerbal;
import frlp.utn.edu.ar.service.FormaVerbalService;
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
 * REST controller for managing {@link frlp.utn.edu.ar.domain.FormaVerbal}.
 */
@RestController
@RequestMapping("/api")
public class FormaVerbalResource {

    private final Logger log = LoggerFactory.getLogger(FormaVerbalResource.class);

    private static final String ENTITY_NAME = "formaVerbal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormaVerbalService formaVerbalService;

    public FormaVerbalResource(FormaVerbalService formaVerbalService) {
        this.formaVerbalService = formaVerbalService;
    }

    /**
     * {@code POST  /forma-verbals} : Create a new formaVerbal.
     *
     * @param formaVerbal the formaVerbal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formaVerbal, or with status {@code 400 (Bad Request)} if the formaVerbal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forma-verbals")
    public ResponseEntity<FormaVerbal> createFormaVerbal(@RequestBody FormaVerbal formaVerbal) throws URISyntaxException {
        log.debug("REST request to save FormaVerbal : {}", formaVerbal);
        if (formaVerbal.getId() != null) {
            throw new BadRequestAlertException("A new formaVerbal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormaVerbal result = formaVerbalService.save(formaVerbal);
        return ResponseEntity.created(new URI("/api/forma-verbals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forma-verbals} : Updates an existing formaVerbal.
     *
     * @param formaVerbal the formaVerbal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaVerbal,
     * or with status {@code 400 (Bad Request)} if the formaVerbal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formaVerbal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forma-verbals")
    public ResponseEntity<FormaVerbal> updateFormaVerbal(@RequestBody FormaVerbal formaVerbal) throws URISyntaxException {
        log.debug("REST request to update FormaVerbal : {}", formaVerbal);
        if (formaVerbal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormaVerbal result = formaVerbalService.save(formaVerbal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaVerbal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /forma-verbals} : get all the formaVerbals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formaVerbals in body.
     */
    @GetMapping("/forma-verbals")
    public List<FormaVerbal> getAllFormaVerbals() {
        log.debug("REST request to get all FormaVerbals");
        return formaVerbalService.findAll();
    }

    /**
     * {@code GET  /forma-verbals/:id} : get the "id" formaVerbal.
     *
     * @param id the id of the formaVerbal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formaVerbal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forma-verbals/{id}")
    public ResponseEntity<FormaVerbal> getFormaVerbal(@PathVariable Long id) {
        log.debug("REST request to get FormaVerbal : {}", id);
        Optional<FormaVerbal> formaVerbal = formaVerbalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formaVerbal);
    }

    /**
     * {@code DELETE  /forma-verbals/:id} : delete the "id" formaVerbal.
     *
     * @param id the id of the formaVerbal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forma-verbals/{id}")
    public ResponseEntity<Void> deleteFormaVerbal(@PathVariable Long id) {
        log.debug("REST request to delete FormaVerbal : {}", id);
        formaVerbalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
