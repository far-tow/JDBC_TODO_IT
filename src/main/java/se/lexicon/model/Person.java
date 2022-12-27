package se.lexicon.model;

import lombok.Data;

@Data
public class Person {
    private final int personId;
    public String firstName;
    public String lastName;

    public Person(int personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}