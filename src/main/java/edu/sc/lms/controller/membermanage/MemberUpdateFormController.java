package edu.sc.lms.controller.membermanage;

import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.model.Member;
import edu.sc.lms.service.custom.impl.MemberManagerServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

public class MemberUpdateFormController {

    @FXML
    public DatePicker dateMembership;
    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXTextField txtMemberName;

    @FXML
    private JFXTextField txtMembershipDate;

    private String selectedMemberId;

    void setSelectedMemberId(String selectedMemberId) {
        System.out.println(selectedMemberId);
        this.selectedMemberId = selectedMemberId;
        loadSelectedMember();
    }

    @FXML
    void btnUpdateMemberOnAction(ActionEvent event) {
        if(MemberManagerServiceImpl.getInstance().updateMember(new Member(selectedMemberId,txtMemberName.getText(),txtContactNumber.getText(),dateMembership.getValue(),null,null))){
            new Alert(Alert.AlertType.INFORMATION,"Member Update Successful").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Member Update Failed").show();
        }
    }

    void loadSelectedMember() {
        if (selectedMemberId != null) {
            Member member = MemberManagerServiceImpl.getInstance().loadSelectedMember(selectedMemberId);
            txtMemberName.setText(member.getName());
            txtContactNumber.setText(member.getContactNumber());
            dateMembership.setValue(member.getMembershipDate());
        }

    }

}
