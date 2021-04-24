package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.Cuestionario;
import frlp.utn.edu.ar.repository.CuestionarioRepository;
import frlp.utn.edu.ar.service.CuestionarioService;
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
 * REST controller for managing {@link frlp.utn.edu.ar.domain.Cuestionario}.
 */
@RestController
@RequestMapping("/api")
public class CuestionarioResource {

    private final Logger log = LoggerFactory.getLogger(CuestionarioResource.class);

    private static final String ENTITY_NAME = "cuestionario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CuestionarioService cuestionarioService;

    private final CuestionarioRepository cuestionarioRepository;

    public CuestionarioResource(CuestionarioService cuestionarioService, CuestionarioRepository cuestionarioRepository) {
        this.cuestionarioService = cuestionarioService;
        this.cuestionarioRepository = cuestionarioRepository;
    }

    /**
     * {@code POST  /cuestionarios} : Create a new cuestionario.
     *
     * @param cuestionario the cuestionario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cuestionario, or with status {@code 400 (Bad Request)} if the cuestionario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cuestionarios")
    public ResponseEntity<Cuestionario> createCuestionario(@RequestBody Cuestionario cuestionario) throws URISyntaxException {
        log.debug("REST request to save Cuestionario : {}", cuestionario);
        if (cuestionario.getId() != null) {
            throw new BadRequestAlertException("A new cuestionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cuestionario result = cuestionarioService.save(cuestionario);
        return ResponseEntity
            .created(new URI("/api/cuestionarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cuestionarios/:id} : Updates an existing cuestionario.
     *
     * @param id the id of the cuestionario to save.
     * @param cuestionario the cuestionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuestionario,
     * or with status {@code 400 (Bad Request)} if the cuestionario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cuestionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cuestionarios/{id}")
    public ResponseEntity<Cuestionario> updateCuestionario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cuestionario cuestionario
    ) throws URISyntaxException {
        log.debug("REST request to update Cuestionario : {}, {}", id, cuestionario);
        if (cuestionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cuestionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cuestionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cuestionario result = cuestionarioService.save(cuestionario);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuestionario.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cuestionarios/:id} : Partial updates given fields of an existing cuestionario, field will ignore if it is null
     *
     * @param id the id of the cuestionario to save.
     * @param cuestionario the cuestionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuestionario,
     * or with status {@code 400 (Bad Request)} if the cuestionario is not valid,
     * or with status {@code 404 (Not Found)} if the cuestionario is not found,
     * or with status {@code 500 (Internal Server Error)} if the cuestionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cuestionarios/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Cuestionario> partialUpdateCuestionario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cuestionario cuestionario
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cuestionario partially : {}, {}", id, cuestionario);
        if (cuestionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cuestionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cuestionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cuestionario> result = cuestionarioService.partialUpdate(cuestionario);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuestionario.getId().toString())
        );
    }

    /**
     * {@code GET  /cuestionarios} : get all the cuestionarios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cuestionarios in body.
     */
    @GetMapping("/cuestionarios")
    public ResponseEntity<List<Cuestionario>> getAllCuestionarios(Pageable pageable) {
        log.debug("REST request to get a page of Cuestionarios");
        Page<Cuestionario> page = cuestionarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cuestionarios/:id} : get the "id" cuestionario.
     *
     * @param id the id of the cuestionario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cuestionario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cuestionarios/{id}")
    public ResponseEntity<Cuestionario> getCuestionario(@PathVariable Long id) {
        log.debug("REST request to get Cuestionario : {}", id);
        Optional<Cuestionario> cuestionario = cuestionarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cuestionario);
    }

    /**
     * {@code DELETE  /cuestionarios/:id} : delete the "id" cuestionario.
     *
     * @param id the id of the cuestionario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cuestionarios/{id}")
    public ResponseEntity<Void> deleteCuestionario(@PathVariable Long id) {
        log.debug("REST request to delete Cuestionario : {}", id);
        cuestionarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
