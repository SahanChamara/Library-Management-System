package edu.sc.lms.controller.bookmanage;

public class BookManageController {
    private static BookManageController instance;
    private BookManageController() {}
    public static BookManageController getInstance(){
        return instance!=null?instance:new BookManageController();
    }
}
