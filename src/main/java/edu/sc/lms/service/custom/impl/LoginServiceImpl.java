package edu.sc.lms.service.custom.impl;

import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.Staff;
import edu.sc.lms.entity.StaffEntity;
import edu.sc.lms.repository.DaoFactory;
import edu.sc.lms.repository.custom.LoginDao;
import edu.sc.lms.service.custom.LoginService;
import edu.sc.lms.util.DaoType;
import org.modelmapper.ModelMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {
    LoginDao loginDao = DaoFactory.getInstance().getDaoType(DaoType.LOGIN);
    ModelMapper mapper = new ModelMapper();

    @Override
    public boolean loginUser(Staff staff) {
        return loginDao.loginUser(mapper.map(staff, StaffEntity.class));
    }

    @Override
    public boolean isExistUser(String email) {
        return loginDao.isExistUser(email);
    }

    @Override
    public boolean updatePassword(String password,String email) {
        return loginDao.updatePassword(password,email);
    }
}
