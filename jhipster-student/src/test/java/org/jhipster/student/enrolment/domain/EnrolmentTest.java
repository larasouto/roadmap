package org.jhipster.student.enrolment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.student.enrolment.domain.CourseTestSamples.*;
import static org.jhipster.student.enrolment.domain.EnrolmentTestSamples.*;
import static org.jhipster.student.enrolment.domain.StudentTestSamples.*;

import org.jhipster.student.enrolment.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnrolmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enrolment.class);
        Enrolment enrolment1 = getEnrolmentSample1();
        Enrolment enrolment2 = new Enrolment();
        assertThat(enrolment1).isNotEqualTo(enrolment2);

        enrolment2.setId(enrolment1.getId());
        assertThat(enrolment1).isEqualTo(enrolment2);

        enrolment2 = getEnrolmentSample2();
        assertThat(enrolment1).isNotEqualTo(enrolment2);
    }

    @Test
    void courseTest() {
        Enrolment enrolment = getEnrolmentRandomSampleGenerator();
        Course courseBack = getCourseRandomSampleGenerator();

        enrolment.setCourse(courseBack);
        assertThat(enrolment.getCourse()).isEqualTo(courseBack);

        enrolment.course(null);
        assertThat(enrolment.getCourse()).isNull();
    }

    @Test
    void studentTest() {
        Enrolment enrolment = getEnrolmentRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        enrolment.setStudent(studentBack);
        assertThat(enrolment.getStudent()).isEqualTo(studentBack);

        enrolment.student(null);
        assertThat(enrolment.getStudent()).isNull();
    }
}
