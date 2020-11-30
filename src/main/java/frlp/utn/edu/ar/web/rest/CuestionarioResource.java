package frlp.utn.edu.ar.web.rest;

import frlp.utn.edu.ar.domain.Cuestionario;
import frlp.utn.edu.ar.service.CuestionarioService;
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

    public CuestionarioResource(CuestionarioService cuestionarioService) {
        this.cuestionarioService = cuestionarioService;
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
        return ResponseEntity.created(new URI("/api/cuestionarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cuestionarios} : Updates an existing cuestionario.
     *
     * @param cuestionario the cuestionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuestionario,
     * or with status {@code 400 (Bad Request)} if the cuestionario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cuestionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cuestionarios")
    public ResponseEntity<Cuestionario> updateCuestionario(@RequestBody Cuestionario cuestionario) throws URISyntaxException {
        log.debug("REST request to update Cuestionario : {}", cuestionario);
        if (cuestionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cuestionario result = cuestionarioService.save(cuestionario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuestionario.getId().toString()))
            .body(result);
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
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
