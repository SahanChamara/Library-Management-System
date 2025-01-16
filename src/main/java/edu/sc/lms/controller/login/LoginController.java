package edu.sc.lms.controller.login;

import com.mysql.cj.log.Log;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.model.Staff;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController implements LoginService{
    private static LoginController instance;

    private LoginController() {
    }
    public static LoginController getInstance(){
        return instance!=null?instance:new LoginController();
    }

    @Override
    public boolean loginUser(Staff staff) {
        try {
            return DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT email,password FROM staff WHERE email='" + staff.getEmail() + "' AND password='" + staff.getPassword() + "'").next();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
