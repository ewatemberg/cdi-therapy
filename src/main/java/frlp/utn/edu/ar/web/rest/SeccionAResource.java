package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.SeccionA;
import frlp.utn.edu.ar.service.SeccionAService;
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
 * REST controller for managing {@link frlp.utn.edu.ar.domain.SeccionA}.
 */
@RestController
@RequestMapping("/api")
public class SeccionAResource {

    private final Logger log = LoggerFactory.getLogger(SeccionAResource.class);

    private static final String ENTITY_NAME = "seccionA";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeccionAService seccionAService;

    public SeccionAResource(SeccionAService seccionAService) {
        this.seccionAService = seccionAService;
    }

    /**
     * {@code POST  /seccion-as} : Create a new seccionA.
     *
     * @param seccionA the seccionA to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seccionA, or with status {@code 400 (Bad Request)} if the seccionA has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seccion-as")
    public ResponseEntity<SeccionA> createSeccionA(@RequestBody SeccionA seccionA) throws URISyntaxException {
        log.debug("REST request to save SeccionA : {}", seccionA);
        if (seccionA.getId() != null) {
            throw new BadRequestAlertException("A new seccionA cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeccionA result = seccionAService.save(seccionA);
        return ResponseEntity.created(new URI("/api/seccion-as/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seccion-as} : Updates an existing seccionA.
     *
     * @param seccionA the seccionA to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seccionA,
     * or with status {@code 400 (Bad Request)} if the seccionA is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seccionA couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seccion-as")
    public ResponseEntity<SeccionA> updateSeccionA(@RequestBody SeccionA seccionA) throws URISyntaxException {
        log.debug("REST request to update SeccionA : {}", seccionA);
        if (seccionA.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SeccionA result = seccionAService.save(seccionA);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seccionA.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /seccion-as} : get all the seccionAS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seccionAS in body.
     */
    @GetMapping("/seccion-as")
    public ResponseEntity<List<SeccionA>> getAllSeccionAS(Pageable pageable) {
        log.debug("REST request to get a page of SeccionAS");
        Page<SeccionA> page = seccionAService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seccion-as/:id} : get the "id" seccionA.
     *
     * @param id the id of the seccionA to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seccionA, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seccion-as/{id}")
    public ResponseEntity<SeccionA> getSeccionA(@PathVariable Long id) {
        log.debug("REST request to get SeccionA : {}", id);
        Optional<SeccionA> seccionA = seccionAService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seccionA);
    }

    /**
     * {@code DELETE  /seccion-as/:id} : delete the "id" seccionA.
     *
     * @param id the id of the seccionA to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seccion-as/{id}")
    public ResponseEntity<Void> deleteSeccionA(@PathVariable Long id) {
        log.debug("REST request to delete SeccionA : {}", id);
        seccionAService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
