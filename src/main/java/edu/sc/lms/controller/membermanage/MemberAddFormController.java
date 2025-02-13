package edu.sc.lms.controller.membermanage;

import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.dto.Member;
import edu.sc.lms.service.ServiceFactory;
import edu.sc.lms.service.custom.MemberService;
import edu.sc.lms.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;

public class MemberAddFormController {

    @FXML
    private DatePicker dateMembership;

    @FXML
    private ImageView imgBookCover;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXTextField txtMemberName;

    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);

    @FXML
    void btnAddMemberOnAction(ActionEvent event) {
        if(memberService.addMember(new Member(null,txtMemberName.getText(),txtContactNumber.getText(),dateMembership.getValue(),null,null))){
            new Alert(Alert.AlertType.INFORMATION,"Member Add Successful").show();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Member Add Failed").show();
        }

    }

}
