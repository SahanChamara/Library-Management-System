package edu.sc.lms.service.custom;

import edu.sc.lms.dto.Member;

import java.util.List;

public interface MemberService {
    Integer totalMembers();
    List<Member> loadMemberTable();
    boolean addMember(Member member);
    String generateMemberId();
    Member loadSelectedMember(String memberId);
    boolean updateMember(Member member);
    boolean deletemember(String memberId);
}
