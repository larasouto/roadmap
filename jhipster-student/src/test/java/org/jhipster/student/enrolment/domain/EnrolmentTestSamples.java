package org.jhipster.student.enrolment.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EnrolmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Enrolment getEnrolmentSample1() {
        return new Enrolment().id(1L);
    }

    public static Enrolment getEnrolmentSample2() {
        return new Enrolment().id(2L);
    }

    public static Enrolment getEnrolmentRandomSampleGenerator() {
        return new Enrolment().id(longCount.incrementAndGet());
    }
}
