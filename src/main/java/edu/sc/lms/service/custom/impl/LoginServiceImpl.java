package edu.sc.lms.service.custom.impl;

import edu.sc.lms.controller.login.LoginFormController;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.Staff;
import edu.sc.lms.entity.StaffEntity;
import edu.sc.lms.repository.DaoFactory;
import edu.sc.lms.repository.custom.LoginDao;
import edu.sc.lms.service.custom.LoginService;
import edu.sc.lms.util.DaoType;
import javafx.scene.control.Alert;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginServiceImpl implements LoginService {
    LoginDao loginDao = DaoFactory.getInstance().getDaoType(DaoType.LOGIN);
    ModelMapper mapper = new ModelMapper();
    private String generateOTP;

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

    @Override
    public boolean sendEmail(String recipentEmail) {
        System.out.println(recipentEmail);
        String randomNumber = RandomStringUtils.randomNumeric(4);
        generateOTP =randomNumber;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "libraryms2025@gmail.com";
        String password = "wucf pmwd yhus ivgh";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });

        Message message = prepareMessage(session, myEmail, recipentEmail, generateOTP);
        if (message != null) {
            System.out.println(message);
            try {
                Transport.send(message);
            } catch (MessagingException e) {
                throw new IllegalArgumentException(e);
            }
            return true;
        }
        return false;
    }

    @Override
    public Message prepareMessage(Session session, String myEmail, String recipentEmail, String generateOTP) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{
                    new InternetAddress(recipentEmail)
            });
            message.setSubject("your LMS Account Password Verification Code");
            String messageBody = "<html>" +
                    "<body style='font-family:Arial,sans-serif; line-height:1.6;'>" +
                    "<h2 style='color:#2E86C1;'>LibraryMS Account OTP Verification</h2>" +
                    "<p>Dear user,</p>" +
                    "<p>Your one-time password (OTP) for accessing your LibraryMS account is:  </p>" +
                    "<h1 style='background-color:#F4F4F4; padding:10px; border-radius:5px; display:inline-block;'>" + generateOTP + "</h1>" +
                    "<p>Please use this OTP to complete your login.</p>" +
                    "<p>If you did not request this, please ignore this email.</p>" +
                    "<br><p>Thank you,<br>LibraryMS Support Team</p>" +
                    "</body>" +
                    "</html>";
            message.setContent(messageBody, "text/html");
            System.out.println("message method"+messageBody);
            return message;
        } catch (Exception e) {
            Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public boolean chekEnterandGeneratedOtp(String enteredOtp) {
        return enteredOtp.equals(generateOTP);
    }
}
