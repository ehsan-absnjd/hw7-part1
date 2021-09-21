package repository;

import entities.Student;
import entities.Teacher;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.*;

public class Repository {
    MariaDbDataSource dataSource;
    Connection connection;

    public Repository(MariaDbDataSource dataSource){
        this.dataSource=dataSource;
        try {
            this.connection =dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        initializeStudentTable();
        initializeTeacherTable();
        initializeMiddleTable();
    }
    public void initializeStudentTable(){
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS student( student_id INT NOT NULL AUTO_INCREMENT, first_name VARCHAR(50), last_name VARCHAR(50), PRIMARY KEY(student_id))");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void initializeTeacherTable(){
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS teacher( personnel_id INT NOT NULL AUTO_INCREMENT, first_name VARCHAR(50), last_name VARCHAR(50), PRIMARY KEY(personnel_id))");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void initializeMiddleTable(){
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS student_teacher( student_id INT NOT NULL , personnel_id INT NOT NULL, FOREIGN KEY(student_id) REFERENCES student(student_id) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY(personnel_id) REFERENCES teacher(personnel_id) ON DELETE CASCADE ON UPDATE CASCADE , PRIMARY KEY(student_id,personnel_id))" );
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addStudent(Student student){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO student( student_id, first_name, last_name) VALUES (?,?,?)");
            statement.setInt(1,student.getStudentId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.executeUpdate( );
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addTeacher(Teacher teacher){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO teacher( personnel_id, first_name, last_name) VALUES (?,?,?)");
            statement.setInt(1,teacher.getPersonnelId());
            statement.setString(2, teacher.getFirstName());
            statement.setString(3,teacher.getLastName());
            statement.executeUpdate( );
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Student[] getStudentsByPersonnelId(int personnelId){
        Student[] students=null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM student_teacher st INNER JOIN student t USING(student_id) WHERE st.personnel_id=?");
            statement.setInt(1,personnelId);
            ResultSet countResult =statement.executeQuery();
            statement.close();
            countResult.next();
            int studentCount = countResult.getInt(1);
            countResult.close();
            if(studentCount>0){
                students = new Student[studentCount];
                PreparedStatement listStatement = connection.prepareStatement("SELECT t.student_id, t.first_name, t.last_name FROM student_teacher st INNER JOIN student t USING(student_id) WHERE st.personnel_id=?");
                listStatement.setInt(1,personnelId);
                ResultSet listResult = listStatement.executeQuery();
                listStatement.close();
                int index=0;
                while(listResult.next()){
                    students[index++]=new Student(listResult.getInt(1),listResult.getString(2), listResult.getString(3));
                }
                listResult.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }

    public void updateStudent(int id ,Student student){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE student SET student_id = ? , first_name=? , last_name=? WHERE student_id=?");
            statement.setInt(1,student.getStudentId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4,id);
            statement.executeUpdate( );
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateTeacher(int id,Teacher teacher){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE teacher SET personnel_id=?, first_name=?, last_name=? WHERE personnel_id=?");
            statement.setInt(1,teacher.getPersonnelId());
            statement.setString(2, teacher.getFirstName());
            statement.setString(3,teacher.getLastName());
            statement.setInt(4,id);
            statement.executeUpdate( );
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeStudent(int id){
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM student WHERE student_id=?");
            statement.setInt(1,id);
            statement.executeUpdate( );
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void removeTeacher(int id){
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM teacher WHERE personnel_id=?");
            statement.setInt(1,id);
            statement.executeUpdate( );
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean checkIfStudentExists(int id){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM student WHERE student_id = ?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery( );
            statement.close();
            boolean exists = resultSet.next();
            resultSet.close();
            return exists;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public boolean checkIfTeacherExists(int id){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM teacher WHERE personnel_id = ?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery( );
            statement.close();
            boolean exists = resultSet.next();
            resultSet.close();
            return exists;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public boolean checkIfStudentTeacherExists(int studentId, int personnelId){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM student_teacher WHERE student_id = ? AND personnel_id = ?");
            statement.setInt(1,studentId);
            statement.setInt(2,personnelId);
            ResultSet resultSet = statement.executeQuery( );
            statement.close();
            boolean exists = resultSet.next();
            resultSet.close();
            return exists;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }
    public Teacher[] getAllTeachers(){
        Teacher[] teachers=null;
        try {
            Statement statement = connection.createStatement();
            ResultSet countResult =statement.executeQuery("SELECT COUNT(*) FROM teacher");
            countResult.next();
            int teacherCount = countResult.getInt(1);
            countResult.close();
            if(teacherCount>0){
                teachers = new Teacher[teacherCount];
                ResultSet listResult = statement.executeQuery("SELECT * FROM teacher");
                statement.close();
                int index=0;
                while(listResult.next()){
                    teachers[index++]=new Teacher(listResult.getInt(1),listResult.getString(2), listResult.getString(3));
                }
                listResult.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return teachers;
    }
    public Student[] getAllStudents(){
        Student[] students=null;
        try {
            Statement statement = connection.createStatement();
            ResultSet countResult =statement.executeQuery("SELECT COUNT(*) FROM student");
            countResult.next();
            int studentCount = countResult.getInt(1);
            countResult.close();
            if(studentCount>0){
                students = new Student[studentCount];
                ResultSet listResult = statement.executeQuery("SELECT * FROM student");
                statement.close();
                int index=0;
                while(listResult.next()){
                    students[index++]=new Student(listResult.getInt(1),listResult.getString(2), listResult.getString(3));
                }
                listResult.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }
    public void addStudentTeacher(int studentId , int personnelId){
        if(checkIfStudentExists(studentId) && checkIfTeacherExists(personnelId)){
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO student_teacher VALUES( ? , ?)");
                statement.setInt(1,studentId);
                statement.setInt(2,personnelId);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
