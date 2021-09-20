package entities;

public class Teacher extends Person {
    private int personnelId;


    public Teacher(int personnelId, String firstName, String lastName) {
        super(firstName,lastName);
        this.personnelId=personnelId;
    }

    public int getPersonnelId() {
        return personnelId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("First Name: ").append(this.getFirstName()).append(", Last Name: ")
                .append(this.getLastName()).append(", Personnel Id: ").append(this.personnelId );
        return stringBuilder.toString();
    }

    public void setPersonnelId(int personnelId) {
        this.personnelId = personnelId;
    }
}
