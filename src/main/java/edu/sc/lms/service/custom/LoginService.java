package edu.sc.lms.service.custom;

import edu.sc.lms.dto.Staff;
import edu.sc.lms.service.SuperService;

import javax.mail.Message;
import javax.mail.Session;

public interface LoginService extends SuperService {
    boolean loginUser(Staff staff);
    boolean isExistUser(String email);
    boolean updatePassword(String password,String email);
    boolean sendEmail(String recipentEmail);
    Message prepareMessage(Session session, String myEmail, String recipentEmail, String generateOTP);
    boolean chekEnterandGeneratedOtp(String enteredOtp);
}
