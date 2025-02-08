package edu.sc.lms.service.custom;

import edu.sc.lms.dto.Staff;
import edu.sc.lms.service.SuperService;

public interface LoginService extends SuperService {
    boolean loginUser(Staff staff);
    boolean isExistUser(String email);
    boolean updatePassword(String password,String email);
}
