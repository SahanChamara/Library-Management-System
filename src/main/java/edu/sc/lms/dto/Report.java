package edu.sc.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Report {
    private String dayOfWeek;
    private Integer bookBorowedCount;
    private String bookCategory;
    private Integer borrowedCountAnBookCategory;
    private Integer activeMemberCount;
    private Double totalFineCollection;
}
