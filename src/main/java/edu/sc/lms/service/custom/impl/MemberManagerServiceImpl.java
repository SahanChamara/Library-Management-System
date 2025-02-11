package edu.sc.lms.service.custom.impl;

import edu.sc.lms.dto.Member;
import edu.sc.lms.entity.MemberEntity;
import edu.sc.lms.repository.DaoFactory;
import edu.sc.lms.repository.custom.MemberManageDao;
import edu.sc.lms.service.custom.MemberService;
import edu.sc.lms.util.DaoType;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

public class MemberManagerServiceImpl implements MemberService {

    MemberManageDao memberManageDao = DaoFactory.getInstance().getDaoType(DaoType.MEMBER);
    ModelMapper mapper = new ModelMapper();

    @Override
    public Integer totalMembers() {
        return memberManageDao.totalMembers();
    }

    @Override
    public List<Member> loadMemberTable() {
        return memberManageDao.getAll()
                .stream()
                .map(memberEntity -> mapper.map(memberEntity, Member.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addMember(Member member) {
        return memberManageDao.add(mapper.map(member, MemberEntity.class));
    }

    @Override
    public Member loadSelectedMember(String memberId) {
        return memberManageDao.loadSelectedMember(memberId);
    }

    @Override
    public boolean updateMember(Member member) {
        return memberManageDao.update(mapper.map(member, MemberEntity.class));
    }

    @Override
    public boolean deleteMember(String memberId) {
        return memberManageDao.delete(memberId);
    }

    @Override
    public Member searchMember(Member member) {
        if (member != null) {
            return mapper.map(memberManageDao.search(mapper.map(member, MemberEntity.class)), Member.class);
        }
        return null;
    }
}
