package entities;

public class Student extends Person {
    private int studentId;


    public Student(int studentId, String firstName, String lastName) {
        super(firstName,lastName);
        this.studentId=studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("First Name: ").append(this.getFirstName()).append(", Last Name: ")
                .append(this.getLastName()).append(", Student Id: ").append(this.studentId);
        return stringBuilder.toString();
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
