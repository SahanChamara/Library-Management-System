package edu.sc.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRecordEntity {
    private String recordId;
    private String memberId;
    private String memberName;
    private String bookId;
    private String bookTitle;
    private LocalDate borrowedDate;
    private LocalDate returnDate;
    private String dateGiven;
    private Integer isReturn;
    private String status;
    private Double fineAmount;

}
