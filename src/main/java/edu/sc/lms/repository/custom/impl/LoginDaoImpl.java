package edu.sc.lms.repository.custom.impl;

import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.entity.StaffEntity;
import edu.sc.lms.repository.custom.LoginDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LoginDaoImpl implements LoginDao {
    @Override
    public boolean loginUser(StaffEntity staffEntity) {
        try {
            return DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT email,password FROM staff WHERE email='" + staffEntity.getEmail() + "' AND password='" + staffEntity.getPassword() + "'").next();
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
    public boolean updatePassword(String password, String email) {
        try {
            PreparedStatement prepareStm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE staff SET password=? WHERE email=?");
            prepareStm.setString(1,password);
            prepareStm.setString(2,email);
            return prepareStm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean add(StaffEntity entity) {
        return false;
    }

    @Override
    public StaffEntity search(StaffEntity entity) {
        return null;
    }

    @Override
    public boolean update(StaffEntity entity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public List<StaffEntity> getAll() {
        return List.of();
    }
}
