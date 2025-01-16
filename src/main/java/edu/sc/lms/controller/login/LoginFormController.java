package edu.sc.lms.controller.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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

    @FXML
    void btnBackMouseClick(MouseEvent event) {

    }

    @FXML
    void btnResetPassword(ActionEvent event) {

    }

    @FXML
    void btnSendCode(ActionEvent event) {

    }

    @FXML
    void btnSignInOnAction(ActionEvent event) {

    }

    @FXML
    void createAccountOnMouseClick(MouseEvent event) {

    }

    @FXML
    void forgotPassOnMouseClick(MouseEvent event) {

    }

}
