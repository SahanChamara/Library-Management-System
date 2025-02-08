package edu.sc.lms.entity;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private String memberId;
    private String name;
    private String contactNumber;
    private LocalDate membershipDate;
    private JFXButton updateMember;
    private JFXButton deleteMember;
}
