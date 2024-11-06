package org.jhipster.student.enrolment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.student.enrolment.domain.StudentIdCardTestSamples.*;
import static org.jhipster.student.enrolment.domain.StudentTestSamples.*;

import org.jhipster.student.enrolment.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudentIdCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentIdCard.class);
        StudentIdCard studentIdCard1 = getStudentIdCardSample1();
        StudentIdCard studentIdCard2 = new StudentIdCard();
        assertThat(studentIdCard1).isNotEqualTo(studentIdCard2);

        studentIdCard2.setId(studentIdCard1.getId());
        assertThat(studentIdCard1).isEqualTo(studentIdCard2);

        studentIdCard2 = getStudentIdCardSample2();
        assertThat(studentIdCard1).isNotEqualTo(studentIdCard2);
    }

    @Test
    void studentTest() {
        StudentIdCard studentIdCard = getStudentIdCardRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        studentIdCard.setStudent(studentBack);
        assertThat(studentIdCard.getStudent()).isEqualTo(studentBack);
        assertThat(studentBack.getStudentIdCard()).isEqualTo(studentIdCard);

        studentIdCard.student(null);
        assertThat(studentIdCard.getStudent()).isNull();
        assertThat(studentBack.getStudentIdCard()).isNull();
    }
}
