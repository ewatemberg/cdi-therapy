package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.FormaVerbal;
import frlp.utn.edu.ar.repository.FormaVerbalRepository;
import frlp.utn.edu.ar.service.FormaVerbalService;
import frlp.utn.edu.ar.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

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

    private final FormaVerbalRepository formaVerbalRepository;

    public FormaVerbalResource(FormaVerbalService formaVerbalService, FormaVerbalRepository formaVerbalRepository) {
        this.formaVerbalService = formaVerbalService;
        this.formaVerbalRepository = formaVerbalRepository;
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
        return ResponseEntity
            .created(new URI("/api/forma-verbals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forma-verbals/:id} : Updates an existing formaVerbal.
     *
     * @param id the id of the formaVerbal to save.
     * @param formaVerbal the formaVerbal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaVerbal,
     * or with status {@code 400 (Bad Request)} if the formaVerbal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formaVerbal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forma-verbals/{id}")
    public ResponseEntity<FormaVerbal> updateFormaVerbal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormaVerbal formaVerbal
    ) throws URISyntaxException {
        log.debug("REST request to update FormaVerbal : {}, {}", id, formaVerbal);
        if (formaVerbal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formaVerbal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formaVerbalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FormaVerbal result = formaVerbalService.save(formaVerbal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaVerbal.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /forma-verbals/:id} : Partial updates given fields of an existing formaVerbal, field will ignore if it is null
     *
     * @param id the id of the formaVerbal to save.
     * @param formaVerbal the formaVerbal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaVerbal,
     * or with status {@code 400 (Bad Request)} if the formaVerbal is not valid,
     * or with status {@code 404 (Not Found)} if the formaVerbal is not found,
     * or with status {@code 500 (Internal Server Error)} if the formaVerbal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/forma-verbals/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FormaVerbal> partialUpdateFormaVerbal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormaVerbal formaVerbal
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormaVerbal partially : {}, {}", id, formaVerbal);
        if (formaVerbal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formaVerbal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formaVerbalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormaVerbal> result = formaVerbalService.partialUpdate(formaVerbal);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaVerbal.getId().toString())
        );
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
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
