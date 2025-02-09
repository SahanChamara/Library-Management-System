package edu.sc.lms.repository.custom;

import edu.sc.lms.dto.Staff;
import edu.sc.lms.entity.StaffEntity;
import edu.sc.lms.repository.CrudDao;

public interface LoginDao extends CrudDao<StaffEntity,String> {
    boolean loginUser(StaffEntity staffEntity);
    boolean isExistUser(String email);
    boolean updatePassword(String password,String email);
}
