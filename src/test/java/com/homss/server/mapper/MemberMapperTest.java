package com.homss.server.mapper;

import com.homss.server.ServerApplicationTests;
import com.homss.server.model.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.homss.server.model.member.MemberStatus.ACTIVE;

public class MemberMapperTest extends ServerApplicationTests {

    @Autowired
    private MemberMapper memberMapper;

    @AfterEach
    void clean() {
        memberMapper.deleteAll();
    }

    @Test
    @DisplayName("멤버 저장")
    void save_test() {
        //given
        Member newMember = Member.of(1L, "member", "url");

        //when
        memberMapper.save(newMember);

        //then
        Assertions.assertThat(memberMapper.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("멤버 모두 삭제")
    void deleteAll_test() {
        //given
        Member member1 = Member.of(1L, "member", "url");
        Member member2 = Member.of(2L, "member", "url");
        memberMapper.save(member1);
        memberMapper.save(member2);

        //when
        memberMapper.deleteAll();

        //then
        Assertions.assertThat(memberMapper.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("멤버 모두 조회")
    void findAll_test() {
        //given
        Member member1 = Member.of(1L, "member", "url");
        Member member2 = Member.of(2L, "member", "url");
        memberMapper.save(member1);
        memberMapper.save(member2);

        //when
        List<Member> allMember = memberMapper.findAll();

        //then
        Assertions.assertThat(allMember.size()).isEqualTo(2);
    }
    @Test
    @DisplayName("소셜 아이디로 멤버 조회")
    void findBySocialId_test() {
        //given
        memberMapper.save(Member.of(1L, "member", "url"));

        //when
        Member member = memberMapper.findBySocialId(1L).orElse(null);

        //then
        Assertions.assertThat(member).isNotNull();
        Assertions.assertThat(member.getSocialId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("사용자 닉네임 중복 확인")
    void checkNicknameDuplicate_test() {
        //given
        String nickname = "member";
        memberMapper.save(Member.of(1L, nickname, "url"));

        //when
        boolean isDuplicate = memberMapper.checkNicknameDuplicate(nickname);

        //then
        Assertions.assertThat(isDuplicate).isTrue();
    }

    @Test
    @DisplayName("사용자 기본정보 변경")
    void changeMemberProfile_test() {
        //given
        String nickname = "nickname";
        String profile = "profile";
        String baekjoonId = "baekjoonId";
        Member newMember = Member.create(1L);
        memberMapper.save(newMember);

        //when
        newMember.changeNickname(nickname);
        newMember.changeProfileImage(profile);
        newMember.changeBaekjoonId(baekjoonId);
        newMember.changeMemberStatus(ACTIVE);
        memberMapper.changeMemberProfile(newMember);

        //then
        Member member = memberMapper.findById(newMember.getMemberId()).orElse(null);
        Assertions.assertThat(member.getNickname()).isEqualTo(nickname);
        Assertions.assertThat(member.getProfileImage()).isEqualTo(profile);
        Assertions.assertThat(member.getBaekjoonId()).isEqualTo(baekjoonId);
        Assertions.assertThat(member.getMemberStatus()).isEqualTo(ACTIVE);
    }

}
