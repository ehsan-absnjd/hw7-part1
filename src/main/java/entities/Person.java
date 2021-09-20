package entities;

public abstract class Person {
    private String firstName;
    private String lastName;
    public Person(String firstName, String lastName){
        this.firstName=firstName;
        this.lastName=lastName;
    }
    public abstract String toString();
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
