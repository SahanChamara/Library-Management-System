package edu.sc.lms.service.custom;

import edu.sc.lms.dto.Member;
import edu.sc.lms.service.SuperService;

import java.util.List;

public interface MemberService extends SuperService {
    Integer totalMembers();
    List<Member> loadMemberTable();
    boolean addMember(Member member);
    Member loadSelectedMember(String memberId);
    boolean updateMember(Member member);
    boolean deleteMember(String memberId);
}
