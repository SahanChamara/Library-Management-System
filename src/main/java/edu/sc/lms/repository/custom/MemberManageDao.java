package edu.sc.lms.repository.custom;

import edu.sc.lms.dto.Member;
import edu.sc.lms.entity.MemberEntity;
import edu.sc.lms.repository.CrudDao;

public interface MemberManageDao extends CrudDao<MemberEntity,String> {
    Integer totalMembers();
    String generateMemberId();
    Member loadSelectedMember(String memberId);
}
