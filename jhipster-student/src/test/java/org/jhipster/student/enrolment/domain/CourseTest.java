package org.jhipster.student.enrolment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.student.enrolment.domain.CourseTestSamples.*;

import org.jhipster.student.enrolment.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Course.class);
        Course course1 = getCourseSample1();
        Course course2 = new Course();
        assertThat(course1).isNotEqualTo(course2);

        course2.setId(course1.getId());
        assertThat(course1).isEqualTo(course2);

        course2 = getCourseSample2();
        assertThat(course1).isNotEqualTo(course2);
    }
}