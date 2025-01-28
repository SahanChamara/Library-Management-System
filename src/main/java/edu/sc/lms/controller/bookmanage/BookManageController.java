package edu.sc.lms.controller.bookmanage;

import com.jfoenix.controls.JFXButton;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.model.Author;
import edu.sc.lms.model.Book;
import edu.sc.lms.model.Category;
import edu.sc.lms.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookManageController implements BookManageService {
    private static BookManageController instance;

    private BookManageController() {
    }

    public static BookManageController getInstance() {
        return instance == null ? instance = new BookManageController() : instance;
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
            while (rst.next()) {
                bookArrayList.add(new Book(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), new JFXButton("Update"), new JFXButton("Delete"), null, null));
            }
            return bookArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean addAuthor(Author author) {
        return false;
    }

    @Override
    public boolean addCategory(Category category) {
        return false;
    }

    @Override
    public boolean addBook(Book book) {
        String bookId = generateBookId();
        String authorId = generateAuthorId();
        String categoryId = generateCategoryId();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            connection.setAutoCommit(false);
            PreparedStatement pst1 = connection.prepareStatement("INSERT INTO category VALUES (?,?)");
            pst1.setString(1, categoryId);
            pst1.setString(2, book.getCategory());
            if (pst1.executeUpdate() > 0) {
                PreparedStatement pst2 = connection.prepareStatement("INSERT INTO author VALUES (?,?)");
                pst2.setString(1, authorId);
                pst2.setString(2, book.getAuthorName());
                if (pst2.executeUpdate() > 0) {
                    PreparedStatement pst3 = connection.prepareStatement("INSERT INTO book VALUES (?,?,?,?,?,?,?,?)");
                    pst3.setString(1, bookId);
                    pst3.setString(2, book.getBookTitle());
                    pst3.setString(3, book.getIsbn());
                    pst3.setDouble(4, book.getPrice());
                    pst3.setString(5, book.getAvailability());
                    pst3.setString(6, book.getBookCoverImg());
                    pst3.setString(7, categoryId);
                    pst3.setString(8, authorId);
                    if (pst3.executeUpdate() > 0) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }

    }

    @Override
    public String generateAuthorId() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT AuthorId FROM author ORDER BY AuthorId DESC LIMIT 1");
            if (rst.next()) {
                return String.format("A%03d", Integer.parseInt(rst.getString(1).substring(1)) + 1);
            }
            return "A001";
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String generateCategoryId() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT CategoryId FROM category ORDER BY CategoryId DESC LIMIT 1");
            if (rst.next()) {
                return String.format("C%03d", Integer.parseInt(rst.getString(1).substring(1)) + 1);
            }
            return "C001";
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String generateBookId() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT BookId FROM book ORDER BY BookId DESC LIMIT 1");
            if (rst.next()) {
                return String.format("B%03d", Integer.parseInt(rst.getString(1).substring(1)) + 1);
            }
            return "B001";
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean deleteBook(String id) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            ResultSet rst = connection.createStatement().executeQuery("SELECT recordId FROM bookrecord WHERE bookId='" + id + "'");
            String recordId = rst.next()? rst.getString(1) :null;

            if(connection.createStatement().executeUpdate("DELETE FROM staff_has_bookrecord WHERE BookRecord_RecordId='" + recordId + "'")>0 &&
                    connection.createStatement().executeUpdate("DELETE FROM bookrecord WHERE bookId='" + id + "'")>0 && connection.createStatement().executeUpdate("DELETE FROM book WHERE bookId='"+id+"'")>0){
                connection.commit();
                return true;
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }finally {
            try {
                assert connection != null;
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }

    }

    @Override
    public boolean updateBook(Book book) {
        try {
            return CrudUtil.execute("UPDATE Book set BookTitle=?,Isbn=?,Price=?,Availability=?,coverImg=? WHERE BookId=?",
                    book.getBookTitle(),
                    book.getIsbn(),
                    book.getPrice(),
                    book.getAvailability(),
                    book.getBookCoverImg(),
                    book.getBookId()
            );
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Book searchBook(Book book) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT " +
                    "b.BookId," +
                    "b.BookTitle," +
                    "b.ISBN," +
                    "b.Price," +
                    "b.Availability," +
                    "b.CoverImg," +
                    "c.Category," +
                    "a.AuthorName FROM book b LEFT JOIN author a ON b.AuthorId=a.AuthorId LEFT JOIN category c ON b.CategoryId=c.CategoryId WHERE BookTitle='" + book.getBookTitle() + "' OR ISBN='" + book.getIsbn() + "'");
            if(rst.next()){
                return new Book(rst.getString(1),rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), new JFXButton("Update"), new JFXButton("Delete"), null, null);
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Book loadSelectedBook(String id) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT " +
                    "b.BookId," +
                    "b.BookTitle," +
                    "b.ISBN," +
                    "b.Price," +
                    "b.Availability," +
                    "b.CoverImg," +
                    "c.Category," +
                    "a.AuthorName FROM book b LEFT JOIN author a ON b.AuthorId=a.AuthorId LEFT JOIN category c ON b.CategoryId=c.CategoryId WHERE BookId='" + id + "'");
            if (rst.next()) {
                return new Book(rst.getString(1),rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), new JFXButton("Update"), new JFXButton("Delete"), null, null);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
