package edu.sc.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StaffEntity {
    private String staffId;
    private String email;
    private String userName;
    private String password;
}
