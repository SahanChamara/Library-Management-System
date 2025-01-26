package edu.sc.lms.controller.membermanage;

import edu.sc.lms.model.Member;

import java.util.List;

public interface MemberService {
    Integer totalMembers();
    List<Member> loadMemberTable();
    String generateMemberId();
    Member loadSelectedMember(String memberId);
}
