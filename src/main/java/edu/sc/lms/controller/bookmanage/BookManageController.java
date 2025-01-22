package edu.sc.lms.controller.bookmanage;

import com.jfoenix.controls.JFXButton;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.model.Book;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookManageController implements BookManageService{
    private static BookManageController instance;
    private BookManageController() {}
    public static BookManageController getInstance(){
        return instance!=null?instance:new BookManageController();
    }

    @Override
    public List<Book> loadBookDetails() {
        ArrayList<Book> bookArrayList = new ArrayList<>();
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT " +
                    "b.BookId," +
                    "b.BookTitle," +
                    "b.ISBN," +
                    "b.Price," +
                    "b.Availability," +
                    "b.CoverImg," +
                    "c.Category," +
                    "a.AuthorName FROM book b LEFT JOIN author a ON b.AuthorId=a.AuthorId LEFT JOIN category c ON b.CategoryId=c.CategoryId");
            while (rst.next()){
                bookArrayList.add(new Book(rst.getString(1), rst.getString(2), rst.getString(3),rst.getDouble(4), rst.getString(5),rst.getString(6), rst.getString(7), rst.getString(8),new JFXButton("Update"),new JFXButton("Delete")));
            }
            return bookArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
