package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.SeccionB;
import frlp.utn.edu.ar.service.SeccionBService;
import frlp.utn.edu.ar.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link frlp.utn.edu.ar.domain.SeccionB}.
 */
@RestController
@RequestMapping("/api")
public class SeccionBResource {

    private final Logger log = LoggerFactory.getLogger(SeccionBResource.class);

    private static final String ENTITY_NAME = "seccionB";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeccionBService seccionBService;

    public SeccionBResource(SeccionBService seccionBService) {
        this.seccionBService = seccionBService;
    }

    /**
     * {@code POST  /seccion-bs} : Create a new seccionB.
     *
     * @param seccionB the seccionB to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seccionB, or with status {@code 400 (Bad Request)} if the seccionB has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seccion-bs")
    public ResponseEntity<SeccionB> createSeccionB(@RequestBody SeccionB seccionB) throws URISyntaxException {
        log.debug("REST request to save SeccionB : {}", seccionB);
        if (seccionB.getId() != null) {
            throw new BadRequestAlertException("A new seccionB cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeccionB result = seccionBService.save(seccionB);
        return ResponseEntity.created(new URI("/api/seccion-bs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seccion-bs} : Updates an existing seccionB.
     *
     * @param seccionB the seccionB to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seccionB,
     * or with status {@code 400 (Bad Request)} if the seccionB is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seccionB couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seccion-bs")
    public ResponseEntity<SeccionB> updateSeccionB(@RequestBody SeccionB seccionB) throws URISyntaxException {
        log.debug("REST request to update SeccionB : {}", seccionB);
        if (seccionB.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SeccionB result = seccionBService.save(seccionB);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seccionB.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /seccion-bs} : get all the seccionBS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seccionBS in body.
     */
    @GetMapping("/seccion-bs")
    public ResponseEntity<List<SeccionB>> getAllSeccionBS(Pageable pageable) {
        log.debug("REST request to get a page of SeccionBS");
        Page<SeccionB> page = seccionBService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seccion-bs/:id} : get the "id" seccionB.
     *
     * @param id the id of the seccionB to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seccionB, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seccion-bs/{id}")
    public ResponseEntity<SeccionB> getSeccionB(@PathVariable Long id) {
        log.debug("REST request to get SeccionB : {}", id);
        Optional<SeccionB> seccionB = seccionBService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seccionB);
    }

    /**
     * {@code DELETE  /seccion-bs/:id} : delete the "id" seccionB.
     *
     * @param id the id of the seccionB to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seccion-bs/{id}")
    public ResponseEntity<Void> deleteSeccionB(@PathVariable Long id) {
        log.debug("REST request to delete SeccionB : {}", id);
        seccionBService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
