package edu.sc.lms.controller.circulation;

import edu.sc.lms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CirculationController implements CirculationService{
    private static CirculationController instance;

    private CirculationController() {
    }

    public static CirculationController getInstance() {
        return instance==null?instance=new CirculationController():instance;
    }

    @Override
    public List<String> loadMemberNames() {
        ArrayList<String> memberArrayList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT Name FROM member");
            while (rst.next()){
                memberArrayList.add(rst.getString(1));
            }
            return memberArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
