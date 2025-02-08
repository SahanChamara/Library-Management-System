package edu.sc.lms.service.custom;

import edu.sc.lms.dto.Staff;

public interface LoginService {
    boolean loginUser(Staff staff);
    boolean isExistUser(String email);
    boolean updatePassword(String password,String email);
}
