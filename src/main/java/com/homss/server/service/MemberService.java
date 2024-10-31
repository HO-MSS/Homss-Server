package com.homss.server.service;

import com.homss.server.model.member.Member;
import com.homss.server.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional
    public Member saveMember() {
        Member member = Member.of(1L, "tester", "profile");
        memberMapper.save(member);
        return memberMapper.findById(member.getMemberId());
    }

    @Transactional(readOnly = true)
    public Member findMemberById(Long memberId) {
        return memberMapper.findById(memberId);
    }

}
