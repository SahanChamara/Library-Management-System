package edu.sc.lms.service.custom.impl;

import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.BookRecord;
import edu.sc.lms.entity.BookRecordEntity;
import edu.sc.lms.repository.DaoFactory;
import edu.sc.lms.repository.custom.CirculationDao;
import edu.sc.lms.service.custom.CirculationService;
import edu.sc.lms.util.CrudUtil;
import edu.sc.lms.util.DaoType;
import org.modelmapper.ModelMapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CirculationServiceImpl implements CirculationService {
    CirculationDao circulationDao = DaoFactory.getInstance().getDaoType(DaoType.CIRCULATION);
    ModelMapper mapper = new ModelMapper();

    @Override
    public List<String> loadMemberNames() {
        return new ArrayList<>(circulationDao.loadMemberNames());
    }

    @Override
    public List<String> loadBookTitle() {
        return new ArrayList<>(circulationDao.loadBookTitle());
    }

    @Override
    public boolean issueBook(BookRecord bookRecord) {
        return circulationDao.issueBook(mapper.map(bookRecord, BookRecordEntity.class));
    }

    @Override
    public List<BookRecord> loadTable() {
        return circulationDao.loadTable()
                .stream()
                .map(entity -> mapper.map(entity, BookRecord.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookRecord loadReturnDetails(String memberName, String bookTitle) {
        return mapper.map(circulationDao.loadReturnDetails(memberName,bookTitle), BookRecord.class);
    }

    @Override
    public List<String> loadBookTitleRe(String memberName) {
//        return new ArrayList<>(circulationDao.loadBookTitleRe(memberName));
        List<String> strings = circulationDao.loadBookTitleRe(memberName);
        return strings;
    }

    @Override
    public void calculateFine() {
        System.out.println("method calling...");
        boolean isUpdated = circulationDao.calculateFine();
        System.out.println(isUpdated);
    }

    @Override
    public Integer borrowedBook() {
       return circulationDao.borrowedBook();
    }

    @Override
    public Integer returnedBookCount() {
        return circulationDao.returnedBookCount();
    }

    @Override
    public boolean returnBook(BookRecord bookRecord) {
        System.out.println(bookRecord.getFineAmount());
        return circulationDao.returnBook(mapper.map(bookRecord, BookRecordEntity.class));
    }
}
