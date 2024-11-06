package org.jhipster.student.enrolment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.student.enrolment.domain.BookTestSamples.*;
import static org.jhipster.student.enrolment.domain.StudentTestSamples.*;

import org.jhipster.student.enrolment.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = getBookSample1();
        Book book2 = new Book();
        assertThat(book1).isNotEqualTo(book2);

        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);

        book2 = getBookSample2();
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    void studentTest() {
        Book book = getBookRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        book.setStudent(studentBack);
        assertThat(book.getStudent()).isEqualTo(studentBack);

        book.student(null);
        assertThat(book.getStudent()).isNull();
    }
}
