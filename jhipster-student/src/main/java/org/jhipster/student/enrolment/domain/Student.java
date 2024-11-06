package org.jhipster.student.enrolment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 2)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 3)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @JsonIgnoreProperties(value = { "student" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private StudentIdCard studentIdCard;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    @JsonIgnoreProperties(value = { "student" }, allowSetters = true)
    private Set<Book> books = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    @JsonIgnoreProperties(value = { "course", "student" }, allowSetters = true)
    private Set<Enrolment> enrolments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Student firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Student lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Student email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return this.age;
    }

    public Student age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public StudentIdCard getStudentIdCard() {
        return this.studentIdCard;
    }

    public void setStudentIdCard(StudentIdCard studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    public Student studentIdCard(StudentIdCard studentIdCard) {
        this.setStudentIdCard(studentIdCard);
        return this;
    }

    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        if (this.books != null) {
            this.books.forEach(i -> i.setStudent(null));
        }
        if (books != null) {
            books.forEach(i -> i.setStudent(this));
        }
        this.books = books;
    }

    public Student books(Set<Book> books) {
        this.setBooks(books);
        return this;
    }

    public Student addBooks(Book book) {
        this.books.add(book);
        book.setStudent(this);
        return this;
    }

    public Student removeBooks(Book book) {
        this.books.remove(book);
        book.setStudent(null);
        return this;
    }

    public Set<Enrolment> getEnrolments() {
        return this.enrolments;
    }

    public void setEnrolments(Set<Enrolment> enrolments) {
        if (this.enrolments != null) {
            this.enrolments.forEach(i -> i.setStudent(null));
        }
        if (enrolments != null) {
            enrolments.forEach(i -> i.setStudent(this));
        }
        this.enrolments = enrolments;
    }

    public Student enrolments(Set<Enrolment> enrolments) {
        this.setEnrolments(enrolments);
        return this;
    }

    public Student addEnrolments(Enrolment enrolment) {
        this.enrolments.add(enrolment);
        enrolment.setStudent(this);
        return this;
    }

    public Student removeEnrolments(Enrolment enrolment) {
        this.enrolments.remove(enrolment);
        enrolment.setStudent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return getId() != null && getId().equals(((Student) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", age=" + getAge() +
            "}";
    }
}
