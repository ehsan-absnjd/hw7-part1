package repository;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void initializeTeacherTable(){
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS teacher( personnel_id INT NOT NULL AUTO_INCREMENT, first_name VARCHAR(50), last_name VARCHAR(50), PRIMARY KEY(personnel_id))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void initializeMiddleTable(){
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS student_teacher( student_id INT NOT NULL , personnel_id INT NOT NULL, FOREIGN KEY(student_id) REFERENCES student(student_id), FOREIGN KEY(personnel_id) REFERENCES teacher(personnel_id))" );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
