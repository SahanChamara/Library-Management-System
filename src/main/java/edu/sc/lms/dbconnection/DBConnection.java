package edu.sc.lms.dbconnection;
import java.sql.*;
public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost/libraryms","root","1234");
    }

    public static DBConnection getInstance() throws SQLException {
        return instance == null ? instance=new DBConnection():instance;
    }

    public Connection getConnection(){
        return connection;
    }

}
