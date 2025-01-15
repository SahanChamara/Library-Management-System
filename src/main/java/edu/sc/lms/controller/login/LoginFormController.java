package edu.sc.lms.controller.login;

import animatefx.animation.FadeInDownBig;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginFormController {

    @FXML
    private AnchorPane paneCreateAnAccount;

    @FXML
    private AnchorPane paneSignIn;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtNewEmail;

    @FXML
    private JFXPasswordField txtNewPassword;

    @FXML
    private JFXTextField txtNewUserName;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    void btnCreateAccountOnAction(ActionEvent event) {

    }

    @FXML
    void btnSignInOnAction(ActionEvent event) {

    }

    @FXML
    void createAccountOnMouseClick(MouseEvent event) {
        paneSignIn.setVisible(false);
        new FadeInDownBig(paneCreateAnAccount).play();
        paneCreateAnAccount.toFront();
    }

    @FXML
    void forgotPassOnMouseClick(MouseEvent event) {

    }

}
