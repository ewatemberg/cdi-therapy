package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.FraseCompleja;
import frlp.utn.edu.ar.repository.FraseComplejaRepository;
import frlp.utn.edu.ar.service.FraseComplejaService;
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
 * REST controller for managing {@link frlp.utn.edu.ar.domain.FraseCompleja}.
 */
@RestController
@RequestMapping("/api")
public class FraseComplejaResource {

    private final Logger log = LoggerFactory.getLogger(FraseComplejaResource.class);

    private static final String ENTITY_NAME = "fraseCompleja";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FraseComplejaService fraseComplejaService;

    private final FraseComplejaRepository fraseComplejaRepository;

    public FraseComplejaResource(FraseComplejaService fraseComplejaService, FraseComplejaRepository fraseComplejaRepository) {
        this.fraseComplejaService = fraseComplejaService;
        this.fraseComplejaRepository = fraseComplejaRepository;
    }

    /**
     * {@code POST  /frase-complejas} : Create a new fraseCompleja.
     *
     * @param fraseCompleja the fraseCompleja to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fraseCompleja, or with status {@code 400 (Bad Request)} if the fraseCompleja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/frase-complejas")
    public ResponseEntity<FraseCompleja> createFraseCompleja(@RequestBody FraseCompleja fraseCompleja) throws URISyntaxException {
        log.debug("REST request to save FraseCompleja : {}", fraseCompleja);
        if (fraseCompleja.getId() != null) {
            throw new BadRequestAlertException("A new fraseCompleja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FraseCompleja result = fraseComplejaService.save(fraseCompleja);
        return ResponseEntity
            .created(new URI("/api/frase-complejas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /frase-complejas/:id} : Updates an existing fraseCompleja.
     *
     * @param id the id of the fraseCompleja to save.
     * @param fraseCompleja the fraseCompleja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraseCompleja,
     * or with status {@code 400 (Bad Request)} if the fraseCompleja is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fraseCompleja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/frase-complejas/{id}")
    public ResponseEntity<FraseCompleja> updateFraseCompleja(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FraseCompleja fraseCompleja
    ) throws URISyntaxException {
        log.debug("REST request to update FraseCompleja : {}, {}", id, fraseCompleja);
        if (fraseCompleja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraseCompleja.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraseComplejaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FraseCompleja result = fraseComplejaService.save(fraseCompleja);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fraseCompleja.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /frase-complejas/:id} : Partial updates given fields of an existing fraseCompleja, field will ignore if it is null
     *
     * @param id the id of the fraseCompleja to save.
     * @param fraseCompleja the fraseCompleja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraseCompleja,
     * or with status {@code 400 (Bad Request)} if the fraseCompleja is not valid,
     * or with status {@code 404 (Not Found)} if the fraseCompleja is not found,
     * or with status {@code 500 (Internal Server Error)} if the fraseCompleja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/frase-complejas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FraseCompleja> partialUpdateFraseCompleja(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FraseCompleja fraseCompleja
    ) throws URISyntaxException {
        log.debug("REST request to partial update FraseCompleja partially : {}, {}", id, fraseCompleja);
        if (fraseCompleja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraseCompleja.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraseComplejaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FraseCompleja> result = fraseComplejaService.partialUpdate(fraseCompleja);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fraseCompleja.getId().toString())
        );
    }

    /**
     * {@code GET  /frase-complejas} : get all the fraseComplejas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fraseComplejas in body.
     */
    @GetMapping("/frase-complejas")
    public List<FraseCompleja> getAllFraseComplejas() {
        log.debug("REST request to get all FraseComplejas");
        return fraseComplejaService.findAll();
    }

    /**
     * {@code GET  /frase-complejas/:id} : get the "id" fraseCompleja.
     *
     * @param id the id of the fraseCompleja to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fraseCompleja, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/frase-complejas/{id}")
    public ResponseEntity<FraseCompleja> getFraseCompleja(@PathVariable Long id) {
        log.debug("REST request to get FraseCompleja : {}", id);
        Optional<FraseCompleja> fraseCompleja = fraseComplejaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fraseCompleja);
    }

    /**
     * {@code DELETE  /frase-complejas/:id} : delete the "id" fraseCompleja.
     *
     * @param id the id of the fraseCompleja to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/frase-complejas/{id}")
    public ResponseEntity<Void> deleteFraseCompleja(@PathVariable Long id) {
        log.debug("REST request to delete FraseCompleja : {}", id);
        fraseComplejaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
