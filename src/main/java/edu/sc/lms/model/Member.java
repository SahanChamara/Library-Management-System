package edu.sc.lms.model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private String memberId;
    private String name;
    private String contactNumber;
    private String membershipDate;
    private JFXButton updateMember;
    private JFXButton deleteMember;
}
