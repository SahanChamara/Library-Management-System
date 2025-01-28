package edu.sc.lms.controller.membermanage;

import com.jfoenix.controls.JFXButton;
import edu.sc.lms.model.Member;
import edu.sc.lms.util.CrudUtil;
import java.sql.Date;
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
                memberArrayList.add(new Member(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDate(4).toLocalDate(),new JFXButton("Update"),new JFXButton("Delete")));
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
            return rst.next() ? new Member(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDate(4).toLocalDate(), null, null) : null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean updateMember(Member member) {
        try {
            return CrudUtil.execute("UPDATE member SET name=?,ContactNumber=?,MembershipDate=? WHERE memberId=?",
                    member.getName(),
                    member.getContactNumber(),
                    member.getMembershipDate(),
                    member.getMemberId()
                    );
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public boolean deletemember(String memberId) {
        try {
            return CrudUtil.execute("DELETE FROM member WHERE memberId='" + memberId + "'");
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
