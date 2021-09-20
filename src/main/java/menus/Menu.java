package menus;

import config.Config;
import entities.Student;
import entities.Teacher;
import org.mariadb.jdbc.MariaDbDataSource;
import repository.Repository;
import utils.Scanner;

import java.sql.SQLException;

public class Menu {
    private MariaDbDataSource dataSource;
    private Repository repository;
    Scanner sc = new Scanner();
    public Menu() throws SQLException {
        dataSource = new MariaDbDataSource();
        dataSource.setUrl(Config.URL);
        dataSource.setUser(Config.USERNAME);
        dataSource.setPassword(Config.PASSWORD);
        repository = new Repository(dataSource);
    }
    public void run() throws SQLException {
        int command;
        do{
            System.out.println("1)+teacher 2)+student 3)-teacher 4)-student 5)show teachers 6)show students 7)update teacher 8)update student 9)show teacher's students 10)assign student to teacher");
            command=sc.getInt();
            switch (command){
                case 1:
                    addTeacher();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    removeTeacher();
                    break;
                case 4:
                    removeStudent();
                    break;
                case 5:
                    showTeachers();
                    break;
                case 6:
                    showStudents();
                    break;
                case 7:
                    updateTeacher();
                    break;
                case 8:
                    updateStudent();
                break;
                case 9:
                    showStudentsByTeacherId();
                    break;
                case 10:
                    assignTeacherStudent();
                    break;
                case 11:
                    System.out.println("bye");
                    break;
                default:
                    System.out.println("invalid command");
                break;
            }
        }while(command!=11);
    }

    private void assignTeacherStudent() {
        System.out.println("personnel id: ");
        int personnelId = sc.getInt();
        while (!repository.checkIfTeacherExists(personnelId)) {
            System.out.println("teacher with this personnel id doesn't exists, try another id");
            personnelId = sc.getInt();
        }
        System.out.println("student id:");
        int studentId = sc.getInt();
        while (!repository.checkIfStudentExists(studentId)) {
            System.out.println("student with this student id doesn't exists, try another id");
            studentId = sc.getInt();
        }
        if (repository.checkIfStudentTeacherExists(studentId, personnelId)) {
            System.out.println("student has already been assigned to this teacher");
        } else {
            repository.addStudentTeacher(studentId, personnelId);
        }
    }
    private void showStudentsByTeacherId() {
        System.out.println("personnel id: ");
        int id= sc.getInt();
        while(!repository.checkIfTeacherExists(id)){
            System.out.println("teacher with this personnel id doesn't exists, try another id");
            id = sc.getInt();
        }
        Student[] students = repository.getStudentsByPersonnelId(id);
        if(students==null){
            System.out.println("there are no students");
        }else for(Student student : students){
            System.out.println(student);
        }
    }

    private void updateStudent() {
        System.out.println("student id to update:");
        int oldId = sc.getInt();
        while(!repository.checkIfStudentExists(oldId)){
            System.out.println("student with this student id doesn't exists, try another id");
            oldId= sc.getInt();
        }
        repository.updateStudent(oldId,createNewStudent());
    }

    private void updateTeacher() {
        System.out.println("personnel id to update: ");
        int oldId= sc.getInt();
        while(!repository.checkIfTeacherExists(oldId)){
            System.out.println("teacher with this personnel id doesn't exists, try another id");
            oldId = sc.getInt();
        }
        repository.updateTeacher(oldId,createNewTeacher());

    }

    private void showStudents() {
        Student[] students =repository.getAllStudents();
        if(students==null){
            System.out.println("there are no students");
        }else for(Student student : students){
            System.out.println(student);
        }
    }

    private void showTeachers() {
        Teacher[] teachers = repository.getAllTeachers();
        if(teachers==null){
            System.out.println("there are no teachers");
        }else for(Teacher teacher : teachers){
            System.out.println(teacher);
        }
    }

    private void removeTeacher() {
        System.out.println("personnel id: ");
        int id= sc.getInt();
        while(!repository.checkIfTeacherExists(id)){
            System.out.println("teacher with this personnel id doesn't exists, try another id");
            id = sc.getInt();
        }
        repository.removeTeacher(id);
    }

    private void removeStudent() {
        System.out.println("student id: ");
        int id= sc.getInt();
        while(!repository.checkIfStudentExists(id)){
            System.out.println("student with this student id doesn't exists, try another id");
            id = sc.getInt();
        }
        repository.removeStudent(id);
    }

    private void addTeacher() {
        repository.addTeacher(createNewTeacher());
    }
    private void addStudent() {
        repository.addStudent(createNewStudent());
    }
    private Student createNewStudent(){
        System.out.println("student id: ");
        int id= sc.getInt();
        while(repository.checkIfStudentExists(id)){
            System.out.println("student with this student id already exists, try another id");
            id = sc.getInt();
        }
        System.out.println("first name: ");
        String firstName = sc.getString();
        System.out.println("last name: ");
        String lastName = sc.getString();
        return new Student(id, firstName, lastName);
    }
    private Teacher createNewTeacher(){
        System.out.println("personnel id: ");
        int id= sc.getInt();
        while(repository.checkIfTeacherExists(id)){
            System.out.println("teacher with this personnel id already exists, try another id");
            id = sc.getInt();
        }
        System.out.println("first name: ");
        String firstName = sc.getString();
        System.out.println("last name: ");
        String lastName = sc.getString();
        return new Teacher(id, firstName, lastName);
    }
}
