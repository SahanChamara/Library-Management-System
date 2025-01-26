package edu.sc.lms.controller.membermanage;

import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MemberUpdateFormController {

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

    }

    void loadSelectedMember() {
        if (selectedMemberId != null) {
            Member member = MemberManagerController.getInstance().loadSelectedMember(selectedMemberId);
            txtMemberName.setText(member.getName());
            txtContactNumber.setText(member.getContactNumber());
            txtMembershipDate.setText(member.getMembershipDate());
        }

    }

}
