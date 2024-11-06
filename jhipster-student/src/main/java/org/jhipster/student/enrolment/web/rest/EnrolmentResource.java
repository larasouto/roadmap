package org.jhipster.student.enrolment.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.student.enrolment.domain.Enrolment;
import org.jhipster.student.enrolment.repository.EnrolmentRepository;
import org.jhipster.student.enrolment.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.student.enrolment.domain.Enrolment}.
 */
@RestController
@RequestMapping("/api/enrolments")
@Transactional
public class EnrolmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(EnrolmentResource.class);

    private static final String ENTITY_NAME = "enrolment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnrolmentRepository enrolmentRepository;

    public EnrolmentResource(EnrolmentRepository enrolmentRepository) {
        this.enrolmentRepository = enrolmentRepository;
    }

    /**
     * {@code POST  /enrolments} : Create a new enrolment.
     *
     * @param enrolment the enrolment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enrolment, or with status {@code 400 (Bad Request)} if the enrolment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Enrolment> createEnrolment(@Valid @RequestBody Enrolment enrolment) throws URISyntaxException {
        LOG.debug("REST request to save Enrolment : {}", enrolment);
        if (enrolment.getId() != null) {
            throw new BadRequestAlertException("A new enrolment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        enrolment = enrolmentRepository.save(enrolment);
        return ResponseEntity.created(new URI("/api/enrolments/" + enrolment.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, enrolment.getId().toString()))
            .body(enrolment);
    }

    /**
     * {@code PUT  /enrolments/:id} : Updates an existing enrolment.
     *
     * @param id the id of the enrolment to save.
     * @param enrolment the enrolment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enrolment,
     * or with status {@code 400 (Bad Request)} if the enrolment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enrolment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Enrolment> updateEnrolment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Enrolment enrolment
    ) throws URISyntaxException {
        LOG.debug("REST request to update Enrolment : {}, {}", id, enrolment);
        if (enrolment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enrolment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enrolmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        enrolment = enrolmentRepository.save(enrolment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, enrolment.getId().toString()))
            .body(enrolment);
    }

    /**
     * {@code PATCH  /enrolments/:id} : Partial updates given fields of an existing enrolment, field will ignore if it is null
     *
     * @param id the id of the enrolment to save.
     * @param enrolment the enrolment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enrolment,
     * or with status {@code 400 (Bad Request)} if the enrolment is not valid,
     * or with status {@code 404 (Not Found)} if the enrolment is not found,
     * or with status {@code 500 (Internal Server Error)} if the enrolment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Enrolment> partialUpdateEnrolment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Enrolment enrolment
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Enrolment partially : {}, {}", id, enrolment);
        if (enrolment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enrolment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enrolmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Enrolment> result = enrolmentRepository
            .findById(enrolment.getId())
            .map(existingEnrolment -> {
                if (enrolment.getCreatedAt() != null) {
                    existingEnrolment.setCreatedAt(enrolment.getCreatedAt());
                }

                return existingEnrolment;
            })
            .map(enrolmentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, enrolment.getId().toString())
        );
    }

    /**
     * {@code GET  /enrolments} : get all the enrolments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enrolments in body.
     */
    @GetMapping("")
    public List<Enrolment> getAllEnrolments() {
        LOG.debug("REST request to get all Enrolments");
        return enrolmentRepository.findAll();
    }

    /**
     * {@code GET  /enrolments/:id} : get the "id" enrolment.
     *
     * @param id the id of the enrolment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enrolment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Enrolment> getEnrolment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Enrolment : {}", id);
        Optional<Enrolment> enrolment = enrolmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(enrolment);
    }

    /**
     * {@code DELETE  /enrolments/:id} : delete the "id" enrolment.
     *
     * @param id the id of the enrolment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrolment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Enrolment : {}", id);
        enrolmentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
