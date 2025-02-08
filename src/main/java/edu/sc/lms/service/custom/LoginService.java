package edu.sc.lms.service.custom;

import edu.sc.lms.model.Staff;

public interface LoginService {
    boolean loginUser(Staff staff);
    boolean isExistUser(String email);
    boolean updatePassword(String password,String email);
}
