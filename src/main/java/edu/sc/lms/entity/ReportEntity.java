package edu.sc.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportEntity {
    private String dayOfWeek;
    private Integer bookBorowedCount;
    private String bookCategory;
    private Integer borrowedCountAnBookCategory;
    private Integer activeMemberCount;
    private Double totalFineCollection;
}
