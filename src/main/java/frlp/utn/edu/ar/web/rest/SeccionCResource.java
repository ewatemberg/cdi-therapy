package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.SeccionC;
import frlp.utn.edu.ar.repository.SeccionCRepository;
import frlp.utn.edu.ar.service.SeccionCService;
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
 * REST controller for managing {@link frlp.utn.edu.ar.domain.SeccionC}.
 */
@RestController
@RequestMapping("/api")
public class SeccionCResource {

    private final Logger log = LoggerFactory.getLogger(SeccionCResource.class);

    private static final String ENTITY_NAME = "seccionC";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeccionCService seccionCService;

    private final SeccionCRepository seccionCRepository;

    public SeccionCResource(SeccionCService seccionCService, SeccionCRepository seccionCRepository) {
        this.seccionCService = seccionCService;
        this.seccionCRepository = seccionCRepository;
    }

    /**
     * {@code POST  /seccion-cs} : Create a new seccionC.
     *
     * @param seccionC the seccionC to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seccionC, or with status {@code 400 (Bad Request)} if the seccionC has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seccion-cs")
    public ResponseEntity<SeccionC> createSeccionC(@RequestBody SeccionC seccionC) throws URISyntaxException {
        log.debug("REST request to save SeccionC : {}", seccionC);
        if (seccionC.getId() != null) {
            throw new BadRequestAlertException("A new seccionC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeccionC result = seccionCService.save(seccionC);
        return ResponseEntity
            .created(new URI("/api/seccion-cs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seccion-cs/:id} : Updates an existing seccionC.
     *
     * @param id the id of the seccionC to save.
     * @param seccionC the seccionC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seccionC,
     * or with status {@code 400 (Bad Request)} if the seccionC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seccionC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seccion-cs/{id}")
    public ResponseEntity<SeccionC> updateSeccionC(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SeccionC seccionC
    ) throws URISyntaxException {
        log.debug("REST request to update SeccionC : {}, {}", id, seccionC);
        if (seccionC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seccionC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seccionCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SeccionC result = seccionCService.save(seccionC);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seccionC.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seccion-cs/:id} : Partial updates given fields of an existing seccionC, field will ignore if it is null
     *
     * @param id the id of the seccionC to save.
     * @param seccionC the seccionC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seccionC,
     * or with status {@code 400 (Bad Request)} if the seccionC is not valid,
     * or with status {@code 404 (Not Found)} if the seccionC is not found,
     * or with status {@code 500 (Internal Server Error)} if the seccionC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/seccion-cs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SeccionC> partialUpdateSeccionC(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SeccionC seccionC
    ) throws URISyntaxException {
        log.debug("REST request to partial update SeccionC partially : {}, {}", id, seccionC);
        if (seccionC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seccionC.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seccionCRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SeccionC> result = seccionCService.partialUpdate(seccionC);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seccionC.getId().toString())
        );
    }

    /**
     * {@code GET  /seccion-cs} : get all the seccionCS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seccionCS in body.
     */
    @GetMapping("/seccion-cs")
    public ResponseEntity<List<SeccionC>> getAllSeccionCS(Pageable pageable) {
        log.debug("REST request to get a page of SeccionCS");
        Page<SeccionC> page = seccionCService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seccion-cs/:id} : get the "id" seccionC.
     *
     * @param id the id of the seccionC to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seccionC, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seccion-cs/{id}")
    public ResponseEntity<SeccionC> getSeccionC(@PathVariable Long id) {
        log.debug("REST request to get SeccionC : {}", id);
        Optional<SeccionC> seccionC = seccionCService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seccionC);
    }

    /**
     * {@code DELETE  /seccion-cs/:id} : delete the "id" seccionC.
     *
     * @param id the id of the seccionC to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seccion-cs/{id}")
    public ResponseEntity<Void> deleteSeccionC(@PathVariable Long id) {
        log.debug("REST request to delete SeccionC : {}", id);
        seccionCService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
