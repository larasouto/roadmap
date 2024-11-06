package org.jhipster.student.enrolment.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStudentAllPropertiesEquals(Student expected, Student actual) {
        assertStudentAutoGeneratedPropertiesEquals(expected, actual);
        assertStudentAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStudentAllUpdatablePropertiesEquals(Student expected, Student actual) {
        assertStudentUpdatableFieldsEquals(expected, actual);
        assertStudentUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStudentAutoGeneratedPropertiesEquals(Student expected, Student actual) {
        assertThat(expected)
            .as("Verify Student auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStudentUpdatableFieldsEquals(Student expected, Student actual) {
        assertThat(expected)
            .as("Verify Student relevant properties")
            .satisfies(e -> assertThat(e.getFirstName()).as("check firstName").isEqualTo(actual.getFirstName()))
            .satisfies(e -> assertThat(e.getLastName()).as("check lastName").isEqualTo(actual.getLastName()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getAge()).as("check age").isEqualTo(actual.getAge()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStudentUpdatableRelationshipsEquals(Student expected, Student actual) {
        assertThat(expected)
            .as("Verify Student relationships")
            .satisfies(e -> assertThat(e.getStudentIdCard()).as("check studentIdCard").isEqualTo(actual.getStudentIdCard()));
    }
}