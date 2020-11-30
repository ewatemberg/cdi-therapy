package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.SeccionC;
import frlp.utn.edu.ar.service.SeccionCService;
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

    public SeccionCResource(SeccionCService seccionCService) {
        this.seccionCService = seccionCService;
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
        return ResponseEntity.created(new URI("/api/seccion-cs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seccion-cs} : Updates an existing seccionC.
     *
     * @param seccionC the seccionC to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seccionC,
     * or with status {@code 400 (Bad Request)} if the seccionC is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seccionC couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seccion-cs")
    public ResponseEntity<SeccionC> updateSeccionC(@RequestBody SeccionC seccionC) throws URISyntaxException {
        log.debug("REST request to update SeccionC : {}", seccionC);
        if (seccionC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SeccionC result = seccionCService.save(seccionC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seccionC.getId().toString()))
            .body(result);
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
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
