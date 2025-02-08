package edu.sc.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Staff {
    private String staffId;
    private String email;
    private String userName;
    private String password;
}
