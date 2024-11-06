package org.jhipster.student.enrolment.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StudentIdCardTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StudentIdCard getStudentIdCardSample1() {
        return new StudentIdCard().id(1L).cardNumber("cardNumber1");
    }

    public static StudentIdCard getStudentIdCardSample2() {
        return new StudentIdCard().id(2L).cardNumber("cardNumber2");
    }

    public static StudentIdCard getStudentIdCardRandomSampleGenerator() {
        return new StudentIdCard().id(longCount.incrementAndGet()).cardNumber(UUID.randomUUID().toString());
    }
}
