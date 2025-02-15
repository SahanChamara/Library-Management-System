package edu.sc.lms.controller.login;

import animatefx.animation.FadeIn;
import animatefx.animation.ZoomIn;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.dto.Staff;
import edu.sc.lms.service.ServiceFactory;
import edu.sc.lms.service.custom.LoginService;
import edu.sc.lms.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFormController {

    public JFXTextField txtCodeNum1;
    public JFXTextField txtCodeNum2;
    public JFXTextField txtCodeNum3;
    public JFXTextField txtCodeNum4;
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
    private String existEmail;

    LoginService loginService = ServiceFactory.getInstance().getServiceType(ServiceType.LOGIN);

    @FXML
    void btnBackMouseClick(MouseEvent event) {

    }

    @FXML
    void btnResetPassword(ActionEvent event) {
        if(txtNewResetPassword.getText().equals(txtNewConfirmPassword.getText())){
            if(loginService.updatePassword(txtNewResetPassword.getText(), existEmail)){
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Password Reset Successful.now you can login your Dashboard.").showAndWait();
                ButtonType buttonType = result.orElse(ButtonType.CLOSE);
                if(buttonType==ButtonType.OK){
                    new FadeIn(paneSignIn).play();
                    paneSignIn.toFront();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Password Reset Failed").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Please Check the Input Password and Try Again").show();
        }
    }

    @FXML
    void btnSendCode(ActionEvent event) {
        try {
            if (loginService.isExistUser(txtEmailEnterPassReset.getText())) {
                sendEmail(txtEmailEnterPassReset.getText());
                existEmail = txtEmailEnterPassReset.getText();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Please Enter Your Account Create Email Address").show();
            }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.INFORMATION,"Please Enter the Email Address").show();
        }
    }

    private void sendEmail(String recipentEmail) {
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
        if (loginService.loginUser(new Staff(null, txtEmail.getText(), null, txtPassword.getText()))) {
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
                stage.show();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
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

    @FXML
    public void btnVerifyCodeOnAction(ActionEvent actionEvent) {
        String otpCode = txtCodeNum1.getText()+txtCodeNum2.getText()+txtCodeNum3.getText()+txtCodeNum4.getText();
        if(otpCode.equals(generateOTP)){
            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Verification Code Match.Now Reset Your Password", ButtonType.OK).showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.CLOSE);
            if(buttonType==ButtonType.OK){
                new FadeIn(paneResetPassword).play();
                paneResetPassword.toFront();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION,"You Enter Verification is Not Match.").show();
        }
        
    }

    public void btnResenOnAction(MouseEvent mouseEvent) {
    }

    public void btnOtpEnterSectionBack(MouseEvent mouseEvent) {
    }
}
