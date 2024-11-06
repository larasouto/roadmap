package org.jhipster.student.enrolment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.student.enrolment.domain.BookTestSamples.*;
import static org.jhipster.student.enrolment.domain.EnrolmentTestSamples.*;
import static org.jhipster.student.enrolment.domain.StudentIdCardTestSamples.*;
import static org.jhipster.student.enrolment.domain.StudentTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.jhipster.student.enrolment.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = getStudentSample1();
        Student student2 = new Student();
        assertThat(student1).isNotEqualTo(student2);

        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);

        student2 = getStudentSample2();
        assertThat(student1).isNotEqualTo(student2);
    }

    @Test
    void studentIdCardTest() {
        Student student = getStudentRandomSampleGenerator();
        StudentIdCard studentIdCardBack = getStudentIdCardRandomSampleGenerator();

        student.setStudentIdCard(studentIdCardBack);
        assertThat(student.getStudentIdCard()).isEqualTo(studentIdCardBack);

        student.studentIdCard(null);
        assertThat(student.getStudentIdCard()).isNull();
    }

    @Test
    void booksTest() {
        Student student = getStudentRandomSampleGenerator();
        Book bookBack = getBookRandomSampleGenerator();

        student.addBooks(bookBack);
        assertThat(student.getBooks()).containsOnly(bookBack);
        assertThat(bookBack.getStudent()).isEqualTo(student);

        student.removeBooks(bookBack);
        assertThat(student.getBooks()).doesNotContain(bookBack);
        assertThat(bookBack.getStudent()).isNull();

        student.books(new HashSet<>(Set.of(bookBack)));
        assertThat(student.getBooks()).containsOnly(bookBack);
        assertThat(bookBack.getStudent()).isEqualTo(student);

        student.setBooks(new HashSet<>());
        assertThat(student.getBooks()).doesNotContain(bookBack);
        assertThat(bookBack.getStudent()).isNull();
    }

    @Test
    void enrolmentsTest() {
        Student student = getStudentRandomSampleGenerator();
        Enrolment enrolmentBack = getEnrolmentRandomSampleGenerator();

        student.addEnrolments(enrolmentBack);
        assertThat(student.getEnrolments()).containsOnly(enrolmentBack);
        assertThat(enrolmentBack.getStudent()).isEqualTo(student);

        student.removeEnrolments(enrolmentBack);
        assertThat(student.getEnrolments()).doesNotContain(enrolmentBack);
        assertThat(enrolmentBack.getStudent()).isNull();

        student.enrolments(new HashSet<>(Set.of(enrolmentBack)));
        assertThat(student.getEnrolments()).containsOnly(enrolmentBack);
        assertThat(enrolmentBack.getStudent()).isEqualTo(student);

        student.setEnrolments(new HashSet<>());
        assertThat(student.getEnrolments()).doesNotContain(enrolmentBack);
        assertThat(enrolmentBack.getStudent()).isNull();
    }
}
