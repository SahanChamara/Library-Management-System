package edu.sc.lms.controller.membermanage;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MemberManagerFormController {

    @FXML
    private TableColumn colBooksBorrowed;

    @FXML
    private TableColumn colContactNumber;

    @FXML
    private TableColumn colDeleteMember;

    @FXML
    private TableColumn colMemberId;

    @FXML
    private TableColumn colMemberName;

    @FXML
    private TableColumn colMembershipDate;

    @FXML
    private TableColumn colUpdateMember;

    @FXML
    private JFXComboBox comboSortBy;

    @FXML
    private Label lblActiveMember;

    @FXML
    private Label lblNewThisMonth;

    @FXML
    private Label lblOverdueBook;

    @FXML
    private Label lblTotalMember;

    @FXML
    private TableView tblMember;

    @FXML
    private JFXTextField txtSearchMember;

    @FXML
    void btnAddMemberOnAction(ActionEvent event) {

    }

    @FXML
    void comboSortByOnStateChange(ActionEvent event) {

    }

    @FXML
    void txtSearchMemberOnAction(ActionEvent event) {

    }

}
