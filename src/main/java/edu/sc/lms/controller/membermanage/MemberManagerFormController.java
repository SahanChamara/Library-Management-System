package edu.sc.lms.controller.membermanage;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.Member;
import edu.sc.lms.service.ServiceFactory;
import edu.sc.lms.service.custom.MemberService;
import edu.sc.lms.util.ServiceType;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
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

    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);

    @FXML
    void btnAddMemberOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/member_adding_form.fxml"))));
        stage.show();
    }

    @FXML
    void comboSortByOnStateChange(ActionEvent event) {

    }

    @FXML
    void txtSearchMemberOnAction(ActionEvent event) {
        Member member = memberService.searchMember(new Member(null, txtSearchMember.getText(), txtSearchMember.getText(), null, null, null));
        member.getUpdateMember().setOnAction(actionEvent2 -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/member_update_form.fxml"));
                Parent load = fxmlLoader.load();
                MemberUpdateFormController controller = fxmlLoader.getController();
                controller.setSelectedMemberId(member.getMemberId());
                Stage stage = new Stage();
                stage.setScene(new Scene(load));
                stage.show();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        });
        member.getDeleteMember().setOnAction(actionEvent2 -> {
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to delete it?..", ButtonType.YES, ButtonType.NO).showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.NO);
            if (buttonType == ButtonType.YES) {
                if (memberService.deleteMember(member.getMemberId())) {
                    new Alert(Alert.AlertType.INFORMATION, "Member Delete Successful").show();
                    loadMemberTable();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Member Delete Failed").show();
                }
            }
        });
        ObservableList<Member> memberObservableList = FXCollections.observableArrayList();
        memberObservableList.add(member);
        tblMember.setItems(memberObservableList);
    }

    void loadMemberTable() {
        ObservableList<Member> memberObservableList = FXCollections.observableArrayList();
        for (Member member : memberService.loadMemberTable()) {
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
            member.getDeleteMember().setOnAction(actionEvent -> {
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to delete it?...", ButtonType.YES, ButtonType.NO).showAndWait();
                ButtonType buttonType = result.orElse(ButtonType.NO);
                if (buttonType == ButtonType.YES) {
                    if (memberService.deleteMember(member.getMemberId())) {
                        new Alert(Alert.AlertType.INFORMATION, "Member Delete Successful").show();
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Member Delete Failed").show();
                    }
                }
            });
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

        lblTotalMember.setText(String.valueOf(memberService.totalMembers()));
        loadMemberTable();
    }

    public void btnGetMembersReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/Member.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            //JasperExportManager.exportReportToPdfFile(jasperPrint,"Book_Report.pdf");
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
