package org.jhipster.student.enrolment.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.student.enrolment.domain.StudentIdCardAsserts.*;
import static org.jhipster.student.enrolment.web.rest.TestUtil.createUpdateProxyForBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.student.enrolment.IntegrationTest;
import org.jhipster.student.enrolment.domain.StudentIdCard;
import org.jhipster.student.enrolment.repository.StudentIdCardRepository;
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
 * Integration tests for the {@link StudentIdCardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudentIdCardResourceIT {

    private static final String DEFAULT_CARD_NUMBER = "AAAAAAAAAAAAAAA";
    private static final String UPDATED_CARD_NUMBER = "BBBBBBBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/student-id-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StudentIdCardRepository studentIdCardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentIdCardMockMvc;

    private StudentIdCard studentIdCard;

    private StudentIdCard insertedStudentIdCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentIdCard createEntity() {
        return new StudentIdCard().cardNumber(DEFAULT_CARD_NUMBER);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentIdCard createUpdatedEntity() {
        return new StudentIdCard().cardNumber(UPDATED_CARD_NUMBER);
    }

    @BeforeEach
    public void initTest() {
        studentIdCard = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedStudentIdCard != null) {
            studentIdCardRepository.delete(insertedStudentIdCard);
            insertedStudentIdCard = null;
        }
    }

    @Test
    @Transactional
    void createStudentIdCard() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the StudentIdCard
        var returnedStudentIdCard = om.readValue(
            restStudentIdCardMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentIdCard)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StudentIdCard.class
        );

        // Validate the StudentIdCard in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStudentIdCardUpdatableFieldsEquals(returnedStudentIdCard, getPersistedStudentIdCard(returnedStudentIdCard));

        insertedStudentIdCard = returnedStudentIdCard;
    }

    @Test
    @Transactional
    void createStudentIdCardWithExistingId() throws Exception {
        // Create the StudentIdCard with an existing ID
        studentIdCard.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentIdCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentIdCard)))
            .andExpect(status().isBadRequest());

        // Validate the StudentIdCard in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCardNumberIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        studentIdCard.setCardNumber(null);

        // Create the StudentIdCard, which fails.

        restStudentIdCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentIdCard)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStudentIdCards() throws Exception {
        // Initialize the database
        insertedStudentIdCard = studentIdCardRepository.saveAndFlush(studentIdCard);

        // Get all the studentIdCardList
        restStudentIdCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentIdCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER)));
    }

    @Test
    @Transactional
    void getStudentIdCard() throws Exception {
        // Initialize the database
        insertedStudentIdCard = studentIdCardRepository.saveAndFlush(studentIdCard);

        // Get the studentIdCard
        restStudentIdCardMockMvc
            .perform(get(ENTITY_API_URL_ID, studentIdCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentIdCard.getId().intValue()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingStudentIdCard() throws Exception {
        // Get the studentIdCard
        restStudentIdCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStudentIdCard() throws Exception {
        // Initialize the database
        insertedStudentIdCard = studentIdCardRepository.saveAndFlush(studentIdCard);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the studentIdCard
        StudentIdCard updatedStudentIdCard = studentIdCardRepository.findById(studentIdCard.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStudentIdCard are not directly saved in db
        em.detach(updatedStudentIdCard);
        updatedStudentIdCard.cardNumber(UPDATED_CARD_NUMBER);

        restStudentIdCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStudentIdCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStudentIdCard))
            )
            .andExpect(status().isOk());

        // Validate the StudentIdCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStudentIdCardToMatchAllProperties(updatedStudentIdCard);
    }

    @Test
    @Transactional
    void putNonExistingStudentIdCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentIdCard.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentIdCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentIdCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(studentIdCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentIdCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudentIdCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentIdCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentIdCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(studentIdCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentIdCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudentIdCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentIdCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentIdCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(studentIdCard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudentIdCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudentIdCardWithPatch() throws Exception {
        // Initialize the database
        insertedStudentIdCard = studentIdCardRepository.saveAndFlush(studentIdCard);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the studentIdCard using partial update
        StudentIdCard partialUpdatedStudentIdCard = new StudentIdCard();
        partialUpdatedStudentIdCard.setId(studentIdCard.getId());

        restStudentIdCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentIdCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStudentIdCard))
            )
            .andExpect(status().isOk());

        // Validate the StudentIdCard in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStudentIdCardUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStudentIdCard, studentIdCard),
            getPersistedStudentIdCard(studentIdCard)
        );
    }

    @Test
    @Transactional
    void fullUpdateStudentIdCardWithPatch() throws Exception {
        // Initialize the database
        insertedStudentIdCard = studentIdCardRepository.saveAndFlush(studentIdCard);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the studentIdCard using partial update
        StudentIdCard partialUpdatedStudentIdCard = new StudentIdCard();
        partialUpdatedStudentIdCard.setId(studentIdCard.getId());

        partialUpdatedStudentIdCard.cardNumber(UPDATED_CARD_NUMBER);

        restStudentIdCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentIdCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStudentIdCard))
            )
            .andExpect(status().isOk());

        // Validate the StudentIdCard in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStudentIdCardUpdatableFieldsEquals(partialUpdatedStudentIdCard, getPersistedStudentIdCard(partialUpdatedStudentIdCard));
    }

    @Test
    @Transactional
    void patchNonExistingStudentIdCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentIdCard.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentIdCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studentIdCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(studentIdCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentIdCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudentIdCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentIdCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentIdCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(studentIdCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentIdCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudentIdCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        studentIdCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentIdCardMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(studentIdCard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudentIdCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudentIdCard() throws Exception {
        // Initialize the database
        insertedStudentIdCard = studentIdCardRepository.saveAndFlush(studentIdCard);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the studentIdCard
        restStudentIdCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, studentIdCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return studentIdCardRepository.count();
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

    protected StudentIdCard getPersistedStudentIdCard(StudentIdCard studentIdCard) {
        return studentIdCardRepository.findById(studentIdCard.getId()).orElseThrow();
    }

    protected void assertPersistedStudentIdCardToMatchAllProperties(StudentIdCard expectedStudentIdCard) {
        assertStudentIdCardAllPropertiesEquals(expectedStudentIdCard, getPersistedStudentIdCard(expectedStudentIdCard));
    }

    protected void assertPersistedStudentIdCardToMatchUpdatableProperties(StudentIdCard expectedStudentIdCard) {
        assertStudentIdCardAllUpdatablePropertiesEquals(expectedStudentIdCard, getPersistedStudentIdCard(expectedStudentIdCard));
    }
}
