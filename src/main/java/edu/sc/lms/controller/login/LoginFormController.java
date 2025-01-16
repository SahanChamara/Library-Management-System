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
        if(LoginController.getInstance().loginUser(new Staff(null,txtEmail.getText(),null,txtPassword.getText()))){
            new Alert(Alert.AlertType.INFORMATION,"Login Successful").show();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Login failed").show();
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
