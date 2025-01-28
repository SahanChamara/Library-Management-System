package edu.sc.lms.controller.membermanage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.model.Member;
import edu.sc.lms.util.CrudUtil;
import io.github.palexdev.materialfx.utils.ScrollUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberManagerController implements MemberService{
    private static MemberManagerController instance;

    private MemberManagerController() {
    }
    public static MemberManagerController getInstance(){
        return instance == null ? instance=new MemberManagerController():instance;
    }

    @Override
    public Integer totalMembers(){
        try {
            ResultSet rst = CrudUtil.execute("SELECT COUNT(MemberId) FROM member");
           return rst.next()?rst.getInt(1):0;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Member> loadMemberTable(){
        ArrayList<Member> memberArrayList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM member");
            while (rst.next()){
                memberArrayList.add(new Member(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),new JFXButton("Update"),new JFXButton("Delete")));
            }
            return memberArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String generateMemberId() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT MemberId FROM member ORDER BY MemberId DESC LIMIT 1");
            if (rst.next()) {
                return String.format("M%03d", Integer.parseInt(rst.getString(1).substring(1)) + 1);
            }
            return "M001";
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Member loadSelectedMember(String memberId) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * from member WHERE memberid='"+memberId+"'");
            return rst.next() ? new Member(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), null, null) : null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean updateMember(Member member) {
        try {
            PreparedStatement psTm = CrudUtil.execute("UPDATE member SET name=?,ContactNumber=?,MembershipDate=? WHERE memberId=?");
            psTm.setString(1,member.getName());
            psTm.setString(2,member.getContactNumber());
            psTm.setString(3,member.getMembershipDate());
            psTm.setString(4,member.getMemberId());
            return psTm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

}
