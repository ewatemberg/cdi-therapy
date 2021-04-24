package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.UsoLenguaje;
import frlp.utn.edu.ar.repository.UsoLenguajeRepository;
import frlp.utn.edu.ar.service.UsoLenguajeService;
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
 * REST controller for managing {@link frlp.utn.edu.ar.domain.UsoLenguaje}.
 */
@RestController
@RequestMapping("/api")
public class UsoLenguajeResource {

    private final Logger log = LoggerFactory.getLogger(UsoLenguajeResource.class);

    private static final String ENTITY_NAME = "usoLenguaje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsoLenguajeService usoLenguajeService;

    private final UsoLenguajeRepository usoLenguajeRepository;

    public UsoLenguajeResource(UsoLenguajeService usoLenguajeService, UsoLenguajeRepository usoLenguajeRepository) {
        this.usoLenguajeService = usoLenguajeService;
        this.usoLenguajeRepository = usoLenguajeRepository;
    }

    /**
     * {@code POST  /uso-lenguajes} : Create a new usoLenguaje.
     *
     * @param usoLenguaje the usoLenguaje to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usoLenguaje, or with status {@code 400 (Bad Request)} if the usoLenguaje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uso-lenguajes")
    public ResponseEntity<UsoLenguaje> createUsoLenguaje(@RequestBody UsoLenguaje usoLenguaje) throws URISyntaxException {
        log.debug("REST request to save UsoLenguaje : {}", usoLenguaje);
        if (usoLenguaje.getId() != null) {
            throw new BadRequestAlertException("A new usoLenguaje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsoLenguaje result = usoLenguajeService.save(usoLenguaje);
        return ResponseEntity
            .created(new URI("/api/uso-lenguajes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uso-lenguajes/:id} : Updates an existing usoLenguaje.
     *
     * @param id the id of the usoLenguaje to save.
     * @param usoLenguaje the usoLenguaje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usoLenguaje,
     * or with status {@code 400 (Bad Request)} if the usoLenguaje is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usoLenguaje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uso-lenguajes/{id}")
    public ResponseEntity<UsoLenguaje> updateUsoLenguaje(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UsoLenguaje usoLenguaje
    ) throws URISyntaxException {
        log.debug("REST request to update UsoLenguaje : {}, {}", id, usoLenguaje);
        if (usoLenguaje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usoLenguaje.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usoLenguajeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UsoLenguaje result = usoLenguajeService.save(usoLenguaje);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usoLenguaje.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /uso-lenguajes/:id} : Partial updates given fields of an existing usoLenguaje, field will ignore if it is null
     *
     * @param id the id of the usoLenguaje to save.
     * @param usoLenguaje the usoLenguaje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usoLenguaje,
     * or with status {@code 400 (Bad Request)} if the usoLenguaje is not valid,
     * or with status {@code 404 (Not Found)} if the usoLenguaje is not found,
     * or with status {@code 500 (Internal Server Error)} if the usoLenguaje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/uso-lenguajes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<UsoLenguaje> partialUpdateUsoLenguaje(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UsoLenguaje usoLenguaje
    ) throws URISyntaxException {
        log.debug("REST request to partial update UsoLenguaje partially : {}, {}", id, usoLenguaje);
        if (usoLenguaje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usoLenguaje.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usoLenguajeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsoLenguaje> result = usoLenguajeService.partialUpdate(usoLenguaje);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usoLenguaje.getId().toString())
        );
    }

    /**
     * {@code GET  /uso-lenguajes} : get all the usoLenguajes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usoLenguajes in body.
     */
    @GetMapping("/uso-lenguajes")
    public List<UsoLenguaje> getAllUsoLenguajes() {
        log.debug("REST request to get all UsoLenguajes");
        return usoLenguajeService.findAll();
    }

    /**
     * {@code GET  /uso-lenguajes/:id} : get the "id" usoLenguaje.
     *
     * @param id the id of the usoLenguaje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usoLenguaje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uso-lenguajes/{id}")
    public ResponseEntity<UsoLenguaje> getUsoLenguaje(@PathVariable Long id) {
        log.debug("REST request to get UsoLenguaje : {}", id);
        Optional<UsoLenguaje> usoLenguaje = usoLenguajeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usoLenguaje);
    }

    /**
     * {@code DELETE  /uso-lenguajes/:id} : delete the "id" usoLenguaje.
     *
     * @param id the id of the usoLenguaje to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uso-lenguajes/{id}")
    public ResponseEntity<Void> deleteUsoLenguaje(@PathVariable Long id) {
        log.debug("REST request to delete UsoLenguaje : {}", id);
        usoLenguajeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
