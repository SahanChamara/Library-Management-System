package edu.sc.lms.repository.custom.impl;

import com.jfoenix.controls.JFXButton;
import edu.sc.lms.dbconnection.DBConnection;
import edu.sc.lms.dto.Member;
import edu.sc.lms.entity.MemberEntity;
import edu.sc.lms.repository.custom.MemberManageDao;
import edu.sc.lms.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberManageDaoImpl implements MemberManageDao {
    @Override
    public Integer totalMembers() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT COUNT(MemberId) FROM member");
            return rst.next() ? rst.getInt(1) : 0;
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
            ResultSet rst = CrudUtil.execute("SELECT * from member WHERE memberid='" + memberId + "'");
            return rst.next() ? new Member(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDate(4).toLocalDate(), null, null) : null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean add(MemberEntity entity) {
        String memberId = generateMemberId();
        try {
            return CrudUtil.execute("INSERT INTO member VALUES (?,?,?,?)",
                    memberId,
                    entity.getName(),
                    entity.getContactNumber(),
                    entity.getMembershipDate()
            );
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public MemberEntity search(MemberEntity entity) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM member WHERE name='"+entity.getName()+"' OR contactNumber='"+entity.getContactNumber()+"'");
            if(rst.next()){
                return new MemberEntity(rst.getString(1), rst.getString(2),rst.getString(3),rst.getDate(4).toLocalDate(),new JFXButton("Update"),new JFXButton("Delete") );
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean update(MemberEntity entity) {
        try {
            return CrudUtil.execute("UPDATE member SET name=?,ContactNumber=?,MembershipDate=? WHERE memberId=?",
                    entity.getName(),
                    entity.getContactNumber(),
                    entity.getMembershipDate(),
                    entity.getMemberId()
            );
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean delete(String s) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            ResultSet rst = connection.createStatement().executeQuery("SELECT recordId FROM bookrecord WHERE memberid='" + s + "'");
            String recordId = rst.next() ? rst.getString(1) : null;

            if (connection.createStatement().executeUpdate("DELETE FROM staff_has_bookrecord WHERE BookRecord_RecordId='" + recordId + "'") > 0 &&
                    connection.createStatement().executeUpdate("DELETE FROM bookrecord WHERE memberid='" + s + "'") > 0 && connection.createStatement().executeUpdate("DELETE FROM member WHERE memberid='" + s + "'") > 0) {
                connection.commit();
                return true;
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new IllegalArgumentException(e);
        } finally {
            try {
                assert connection != null;
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public List<MemberEntity> getAll() {
        ArrayList<MemberEntity> memberArrayList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM member");
            while (rst.next()){
                memberArrayList.add(new MemberEntity(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDate(4).toLocalDate(),new JFXButton("Update"),new JFXButton("Delete")));
            }
            return memberArrayList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
