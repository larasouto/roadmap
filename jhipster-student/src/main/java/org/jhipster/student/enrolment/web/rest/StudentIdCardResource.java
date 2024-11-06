package org.jhipster.student.enrolment.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.jhipster.student.enrolment.domain.StudentIdCard;
import org.jhipster.student.enrolment.repository.StudentIdCardRepository;
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
 * REST controller for managing {@link org.jhipster.student.enrolment.domain.StudentIdCard}.
 */
@RestController
@RequestMapping("/api/student-id-cards")
@Transactional
public class StudentIdCardResource {

    private static final Logger LOG = LoggerFactory.getLogger(StudentIdCardResource.class);

    private static final String ENTITY_NAME = "studentIdCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentIdCardRepository studentIdCardRepository;

    public StudentIdCardResource(StudentIdCardRepository studentIdCardRepository) {
        this.studentIdCardRepository = studentIdCardRepository;
    }

    /**
     * {@code POST  /student-id-cards} : Create a new studentIdCard.
     *
     * @param studentIdCard the studentIdCard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentIdCard, or with status {@code 400 (Bad Request)} if the studentIdCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StudentIdCard> createStudentIdCard(@Valid @RequestBody StudentIdCard studentIdCard) throws URISyntaxException {
        LOG.debug("REST request to save StudentIdCard : {}", studentIdCard);
        if (studentIdCard.getId() != null) {
            throw new BadRequestAlertException("A new studentIdCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        studentIdCard = studentIdCardRepository.save(studentIdCard);
        return ResponseEntity.created(new URI("/api/student-id-cards/" + studentIdCard.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, studentIdCard.getId().toString()))
            .body(studentIdCard);
    }

    /**
     * {@code PUT  /student-id-cards/:id} : Updates an existing studentIdCard.
     *
     * @param id the id of the studentIdCard to save.
     * @param studentIdCard the studentIdCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentIdCard,
     * or with status {@code 400 (Bad Request)} if the studentIdCard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentIdCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentIdCard> updateStudentIdCard(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StudentIdCard studentIdCard
    ) throws URISyntaxException {
        LOG.debug("REST request to update StudentIdCard : {}, {}", id, studentIdCard);
        if (studentIdCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentIdCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentIdCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        studentIdCard = studentIdCardRepository.save(studentIdCard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studentIdCard.getId().toString()))
            .body(studentIdCard);
    }

    /**
     * {@code PATCH  /student-id-cards/:id} : Partial updates given fields of an existing studentIdCard, field will ignore if it is null
     *
     * @param id the id of the studentIdCard to save.
     * @param studentIdCard the studentIdCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentIdCard,
     * or with status {@code 400 (Bad Request)} if the studentIdCard is not valid,
     * or with status {@code 404 (Not Found)} if the studentIdCard is not found,
     * or with status {@code 500 (Internal Server Error)} if the studentIdCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StudentIdCard> partialUpdateStudentIdCard(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StudentIdCard studentIdCard
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update StudentIdCard partially : {}, {}", id, studentIdCard);
        if (studentIdCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentIdCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentIdCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StudentIdCard> result = studentIdCardRepository
            .findById(studentIdCard.getId())
            .map(existingStudentIdCard -> {
                if (studentIdCard.getCardNumber() != null) {
                    existingStudentIdCard.setCardNumber(studentIdCard.getCardNumber());
                }

                return existingStudentIdCard;
            })
            .map(studentIdCardRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studentIdCard.getId().toString())
        );
    }

    /**
     * {@code GET  /student-id-cards} : get all the studentIdCards.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentIdCards in body.
     */
    @GetMapping("")
    public List<StudentIdCard> getAllStudentIdCards(@RequestParam(name = "filter", required = false) String filter) {
        if ("student-is-null".equals(filter)) {
            LOG.debug("REST request to get all StudentIdCards where student is null");
            return StreamSupport.stream(studentIdCardRepository.findAll().spliterator(), false)
                .filter(studentIdCard -> studentIdCard.getStudent() == null)
                .toList();
        }
        LOG.debug("REST request to get all StudentIdCards");
        return studentIdCardRepository.findAll();
    }

    /**
     * {@code GET  /student-id-cards/:id} : get the "id" studentIdCard.
     *
     * @param id the id of the studentIdCard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentIdCard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentIdCard> getStudentIdCard(@PathVariable("id") Long id) {
        LOG.debug("REST request to get StudentIdCard : {}", id);
        Optional<StudentIdCard> studentIdCard = studentIdCardRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studentIdCard);
    }

    /**
     * {@code DELETE  /student-id-cards/:id} : delete the "id" studentIdCard.
     *
     * @param id the id of the studentIdCard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentIdCard(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete StudentIdCard : {}", id);
        studentIdCardRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
