package edu.sc.lms.controller.membermanage;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.model.Member;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MemberManagerFormController implements Initializable {

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

    void loadMemberTable(){
        ObservableList<Member> memberObservableList = FXCollections.observableArrayList();
        for (Member member : MemberManagerController.getInstance().loadMemberTable()) {
            member.getUpdateMember().setOnAction(actionEvent -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/member_update_form.fxml"));
                    Parent load = fxmlLoader.load();
                    MemberUpdateFormController controller = fxmlLoader.getController();
                    controller.setSelectedMemberId(member.getMemberId());
                    Stage stage = new Stage();
                    stage.setScene(new Scene(load));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
            member.getDeleteMember().setOnAction(actionEvent -> System.out.println("Delete btn clicked"));
            memberObservableList.add(member);
        }
        tblMember.setItems(memberObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colMembershipDate.setCellValueFactory(new PropertyValueFactory<>("membershipDate"));
        colUpdateMember.setCellValueFactory(new PropertyValueFactory<>("updateMember"));
        colDeleteMember.setCellValueFactory(new PropertyValueFactory<>("deleteMember"));

        lblTotalMember.setText(String.valueOf(MemberManagerController.getInstance().totalMembers()));
        loadMemberTable();
    }
}
