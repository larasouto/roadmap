package org.jhipster.student.enrolment.repository;

import org.jhipster.student.enrolment.domain.Enrolment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Enrolment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, Long> {}
