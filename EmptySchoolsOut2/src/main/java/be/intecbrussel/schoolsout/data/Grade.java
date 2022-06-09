package be.intecbrussel.schoolsout.data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Grade {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Person person;
    @ManyToOne (cascade = {CascadeType.PERSIST})
    private Course course;
    private BigDecimal gradeValue;
    private LocalDate date;

    public Grade() {
    }

    public Grade(Person person, Course course, BigDecimal gradeValue, LocalDate date) {
        this.person = person;
        this.course = course;
        this.gradeValue = gradeValue;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public BigDecimal getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(BigDecimal gradeValue) {
        this.gradeValue = gradeValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return Objects.equals(id, grade.id) && Objects.equals(person, grade.person) && Objects.equals(course, grade.course) && Objects.equals(gradeValue, grade.gradeValue) && Objects.equals(date, grade.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, course, gradeValue, date);
    }

    @Override
    public String toString() {
        return "Grade{id " + id +
                ", student id=" + person.getId() +
                ", person=" + person.getFirstName() + " " + person.getFamilyName() +
                ", course=" + course.getName() +
                ", gradeValue=" + gradeValue +
                ", date=" + date +
                '}';
    }


}
