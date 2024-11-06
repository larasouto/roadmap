package org.jhipster.student.enrolment.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.student.enrolment.domain.EnrolmentAsserts.*;
import static org.jhipster.student.enrolment.web.rest.TestUtil.createUpdateProxyForBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.student.enrolment.IntegrationTest;
import org.jhipster.student.enrolment.domain.Enrolment;
import org.jhipster.student.enrolment.repository.EnrolmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EnrolmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnrolmentResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/enrolments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnrolmentMockMvc;

    private Enrolment enrolment;

    private Enrolment insertedEnrolment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enrolment createEntity() {
        return new Enrolment().createdAt(DEFAULT_CREATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enrolment createUpdatedEntity() {
        return new Enrolment().createdAt(UPDATED_CREATED_AT);
    }

    @BeforeEach
    public void initTest() {
        enrolment = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEnrolment != null) {
            enrolmentRepository.delete(insertedEnrolment);
            insertedEnrolment = null;
        }
    }

    @Test
    @Transactional
    void createEnrolment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Enrolment
        var returnedEnrolment = om.readValue(
            restEnrolmentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enrolment)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Enrolment.class
        );

        // Validate the Enrolment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEnrolmentUpdatableFieldsEquals(returnedEnrolment, getPersistedEnrolment(returnedEnrolment));

        insertedEnrolment = returnedEnrolment;
    }

    @Test
    @Transactional
    void createEnrolmentWithExistingId() throws Exception {
        // Create the Enrolment with an existing ID
        enrolment.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrolmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enrolment)))
            .andExpect(status().isBadRequest());

        // Validate the Enrolment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        enrolment.setCreatedAt(null);

        // Create the Enrolment, which fails.

        restEnrolmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enrolment)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEnrolments() throws Exception {
        // Initialize the database
        insertedEnrolment = enrolmentRepository.saveAndFlush(enrolment);

        // Get all the enrolmentList
        restEnrolmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrolment.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getEnrolment() throws Exception {
        // Initialize the database
        insertedEnrolment = enrolmentRepository.saveAndFlush(enrolment);

        // Get the enrolment
        restEnrolmentMockMvc
            .perform(get(ENTITY_API_URL_ID, enrolment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enrolment.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEnrolment() throws Exception {
        // Get the enrolment
        restEnrolmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnrolment() throws Exception {
        // Initialize the database
        insertedEnrolment = enrolmentRepository.saveAndFlush(enrolment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enrolment
        Enrolment updatedEnrolment = enrolmentRepository.findById(enrolment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEnrolment are not directly saved in db
        em.detach(updatedEnrolment);
        updatedEnrolment.createdAt(UPDATED_CREATED_AT);

        restEnrolmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnrolment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEnrolment))
            )
            .andExpect(status().isOk());

        // Validate the Enrolment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEnrolmentToMatchAllProperties(updatedEnrolment);
    }

    @Test
    @Transactional
    void putNonExistingEnrolment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enrolment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrolmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enrolment.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enrolment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enrolment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnrolment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enrolment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnrolmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enrolment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enrolment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnrolment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enrolment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnrolmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enrolment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enrolment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnrolmentWithPatch() throws Exception {
        // Initialize the database
        insertedEnrolment = enrolmentRepository.saveAndFlush(enrolment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enrolment using partial update
        Enrolment partialUpdatedEnrolment = new Enrolment();
        partialUpdatedEnrolment.setId(enrolment.getId());

        partialUpdatedEnrolment.createdAt(UPDATED_CREATED_AT);

        restEnrolmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnrolment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnrolment))
            )
            .andExpect(status().isOk());

        // Validate the Enrolment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnrolmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEnrolment, enrolment),
            getPersistedEnrolment(enrolment)
        );
    }

    @Test
    @Transactional
    void fullUpdateEnrolmentWithPatch() throws Exception {
        // Initialize the database
        insertedEnrolment = enrolmentRepository.saveAndFlush(enrolment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enrolment using partial update
        Enrolment partialUpdatedEnrolment = new Enrolment();
        partialUpdatedEnrolment.setId(enrolment.getId());

        partialUpdatedEnrolment.createdAt(UPDATED_CREATED_AT);

        restEnrolmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnrolment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnrolment))
            )
            .andExpect(status().isOk());

        // Validate the Enrolment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnrolmentUpdatableFieldsEquals(partialUpdatedEnrolment, getPersistedEnrolment(partialUpdatedEnrolment));
    }

    @Test
    @Transactional
    void patchNonExistingEnrolment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enrolment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrolmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enrolment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enrolment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enrolment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnrolment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enrolment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnrolmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enrolment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enrolment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnrolment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enrolment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnrolmentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(enrolment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enrolment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnrolment() throws Exception {
        // Initialize the database
        insertedEnrolment = enrolmentRepository.saveAndFlush(enrolment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the enrolment
        restEnrolmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, enrolment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return enrolmentRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Enrolment getPersistedEnrolment(Enrolment enrolment) {
        return enrolmentRepository.findById(enrolment.getId()).orElseThrow();
    }

    protected void assertPersistedEnrolmentToMatchAllProperties(Enrolment expectedEnrolment) {
        assertEnrolmentAllPropertiesEquals(expectedEnrolment, getPersistedEnrolment(expectedEnrolment));
    }

    protected void assertPersistedEnrolmentToMatchUpdatableProperties(Enrolment expectedEnrolment) {
        assertEnrolmentAllUpdatablePropertiesEquals(expectedEnrolment, getPersistedEnrolment(expectedEnrolment));
    }
}
