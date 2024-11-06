package org.jhipster.student.enrolment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A StudentIdCard.
 */
@Entity
@Table(name = "student_id_card")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StudentIdCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 15, max = 15)
    @Column(name = "card_number", length = 15, nullable = false)
    private String cardNumber;

    @JsonIgnoreProperties(value = { "studentIdCard", "books", "enrolments" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "studentIdCard")
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StudentIdCard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public StudentIdCard cardNumber(String cardNumber) {
        this.setCardNumber(cardNumber);
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        if (this.student != null) {
            this.student.setStudentIdCard(null);
        }
        if (student != null) {
            student.setStudentIdCard(this);
        }
        this.student = student;
    }

    public StudentIdCard student(Student student) {
        this.setStudent(student);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentIdCard)) {
            return false;
        }
        return getId() != null && getId().equals(((StudentIdCard) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentIdCard{" +
            "id=" + getId() +
            ", cardNumber='" + getCardNumber() + "'" +
            "}";
    }
}
