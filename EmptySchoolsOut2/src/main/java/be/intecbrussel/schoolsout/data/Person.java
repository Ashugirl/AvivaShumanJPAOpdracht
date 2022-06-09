package be.intecbrussel.schoolsout.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String familyName;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Person() {
    }

    public Person(String firstName, String familyName, Gender gender) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(familyName, person.familyName) && gender == person.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, familyName, gender);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", gender=" + gender +
                '}';
    }
}
