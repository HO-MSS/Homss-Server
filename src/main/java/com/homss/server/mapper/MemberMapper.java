package com.homss.server.mapper;

import com.homss.server.model.member.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    void save(Member member);
    void deleteAll();
    List<Member> findAll();
    Optional<Member> findBySocialId(Long socialId);
    Member findById(Long id);
    boolean checkDuplicateNickname(String nickname);
    void changeMemberProfile(Member member);
}
