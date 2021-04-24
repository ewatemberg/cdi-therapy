package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.SeccionD;
import frlp.utn.edu.ar.repository.SeccionDRepository;
import frlp.utn.edu.ar.service.SeccionDService;
import frlp.utn.edu.ar.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link frlp.utn.edu.ar.domain.SeccionD}.
 */
@RestController
@RequestMapping("/api")
public class SeccionDResource {

    private final Logger log = LoggerFactory.getLogger(SeccionDResource.class);

    private static final String ENTITY_NAME = "seccionD";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeccionDService seccionDService;

    private final SeccionDRepository seccionDRepository;

    public SeccionDResource(SeccionDService seccionDService, SeccionDRepository seccionDRepository) {
        this.seccionDService = seccionDService;
        this.seccionDRepository = seccionDRepository;
    }

    /**
     * {@code POST  /seccion-ds} : Create a new seccionD.
     *
     * @param seccionD the seccionD to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seccionD, or with status {@code 400 (Bad Request)} if the seccionD has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seccion-ds")
    public ResponseEntity<SeccionD> createSeccionD(@RequestBody SeccionD seccionD) throws URISyntaxException {
        log.debug("REST request to save SeccionD : {}", seccionD);
        if (seccionD.getId() != null) {
            throw new BadRequestAlertException("A new seccionD cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeccionD result = seccionDService.save(seccionD);
        return ResponseEntity
            .created(new URI("/api/seccion-ds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seccion-ds/:id} : Updates an existing seccionD.
     *
     * @param id the id of the seccionD to save.
     * @param seccionD the seccionD to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seccionD,
     * or with status {@code 400 (Bad Request)} if the seccionD is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seccionD couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seccion-ds/{id}")
    public ResponseEntity<SeccionD> updateSeccionD(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SeccionD seccionD
    ) throws URISyntaxException {
        log.debug("REST request to update SeccionD : {}, {}", id, seccionD);
        if (seccionD.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seccionD.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seccionDRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SeccionD result = seccionDService.save(seccionD);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seccionD.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seccion-ds/:id} : Partial updates given fields of an existing seccionD, field will ignore if it is null
     *
     * @param id the id of the seccionD to save.
     * @param seccionD the seccionD to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seccionD,
     * or with status {@code 400 (Bad Request)} if the seccionD is not valid,
     * or with status {@code 404 (Not Found)} if the seccionD is not found,
     * or with status {@code 500 (Internal Server Error)} if the seccionD couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/seccion-ds/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SeccionD> partialUpdateSeccionD(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SeccionD seccionD
    ) throws URISyntaxException {
        log.debug("REST request to partial update SeccionD partially : {}, {}", id, seccionD);
        if (seccionD.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seccionD.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seccionDRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SeccionD> result = seccionDService.partialUpdate(seccionD);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seccionD.getId().toString())
        );
    }

    /**
     * {@code GET  /seccion-ds} : get all the seccionDS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seccionDS in body.
     */
    @GetMapping("/seccion-ds")
    public ResponseEntity<List<SeccionD>> getAllSeccionDS(Pageable pageable) {
        log.debug("REST request to get a page of SeccionDS");
        Page<SeccionD> page = seccionDService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seccion-ds/:id} : get the "id" seccionD.
     *
     * @param id the id of the seccionD to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seccionD, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seccion-ds/{id}")
    public ResponseEntity<SeccionD> getSeccionD(@PathVariable Long id) {
        log.debug("REST request to get SeccionD : {}", id);
        Optional<SeccionD> seccionD = seccionDService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seccionD);
    }

    /**
     * {@code DELETE  /seccion-ds/:id} : delete the "id" seccionD.
     *
     * @param id the id of the seccionD to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seccion-ds/{id}")
    public ResponseEntity<Void> deleteSeccionD(@PathVariable Long id) {
        log.debug("REST request to delete SeccionD : {}", id);
        seccionDService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
