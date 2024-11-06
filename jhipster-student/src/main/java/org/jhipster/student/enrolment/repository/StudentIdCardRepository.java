package org.jhipster.student.enrolment.repository;

import org.jhipster.student.enrolment.domain.StudentIdCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StudentIdCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentIdCardRepository extends JpaRepository<StudentIdCard, Long> {}
