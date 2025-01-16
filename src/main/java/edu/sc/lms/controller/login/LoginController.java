package edu.sc.lms.controller.login;

import com.mysql.cj.log.Log;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.model.Staff;

import java.sql.PreparedStatement;
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

    @Override
    public boolean isExistUser(String email) {
        try {
            return DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT email FROM staff WHERE email='" + email + "'").next();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean updatePassword(String password,String email) {
        try {
            PreparedStatement prepareStm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE staff SET password=? WHERE email=?");
            prepareStm.setString(1,password);
            prepareStm.setString(2,email);
            return prepareStm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
