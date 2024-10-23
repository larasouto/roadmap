package com.student.enrolment.repositories;

import org.springframework.data.repository.CrudRepository;

import com.student.enrolment.models.StudentIdCard;

public interface StudentIdCardRepository extends CrudRepository<StudentIdCard, Long> {
}
