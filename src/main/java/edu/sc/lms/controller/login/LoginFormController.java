package edu.sc.lms.controller.login;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.ZoomIn;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.model.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFormController {

    @FXML
    private AnchorPane paneEnterOTP;

    @FXML
    private AnchorPane paneForgotPassword;

    @FXML
    private AnchorPane paneResetPassword;

    @FXML
    private AnchorPane paneSignIn;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtEmailEnterPassReset;

    @FXML
    private JFXTextField txtNewConfirmPassword;

    @FXML
    private JFXTextField txtNewResetPassword;

    @FXML
    private JFXPasswordField txtPassword;

    private String generateOTP;

    @FXML
    void btnBackMouseClick(MouseEvent event) {

    }

    @FXML
    void btnResetPassword(ActionEvent event) {

    }

    @FXML
    void btnSendCode(ActionEvent event) {
        String recipentEmail = txtEmailEnterPassReset.getText();
        sendEmail(recipentEmail);
    }

    private void sendEmail(String recipentEmail) {
        String randomNumber = RandomStringUtils.randomNumeric(4);
        generateOTP = "Your Verification Code - " + randomNumber;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "softhiive@gmail.com";
        String password = "fwow uhmt rwpr kznj";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });

        Message message = prepareMessage(session, myEmail, recipentEmail, generateOTP);
        System.out.println(message);
        if (message != null) {
            new Alert(Alert.AlertType.INFORMATION, "Your Verification Code is send you email").show();
            paneEnterOTP.toFront();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Please Try Again").show();
        }
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Message prepareMessage(Session session, String myEmail, String recipentEmail, String generateOTP) {
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
            return message;
        } catch (Exception e) {
            Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }


    @FXML
    void btnSignInOnAction(ActionEvent event) {
        if (LoginController.getInstance().loginUser(new Staff(null, txtEmail.getText(), null, txtPassword.getText()))) {
            new Alert(Alert.AlertType.INFORMATION, "Login Successful").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Login failed").show();
        }
    }

    @FXML
    void createAccountOnMouseClick(MouseEvent event) {

    }

    @FXML
    void forgotPassOnMouseClick(MouseEvent event) {
        new FadeIn(paneForgotPassword).play();
        paneForgotPassword.toFront();
    }

    @FXML
    public void forgotPassFormBack(MouseEvent mouseEvent) {
        paneSignIn.toFront();
        new ZoomIn(paneSignIn).play();

    }
}
