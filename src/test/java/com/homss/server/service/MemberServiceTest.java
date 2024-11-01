package com.homss.server.service;

import com.homss.server.ServerApplicationTests;
import com.homss.server.dto.request.EditMemberProfileRequest;
import com.homss.server.dto.response.MemberNicknameDuplicateResponse;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.homss.server.model.member.MemberStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTest extends ServerApplicationTests {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberMapper memberMapper;

    @AfterEach
    void clean() {
        memberMapper.deleteAll();
    }


    @Test
    @DisplayName("닉네임 중복 체크")
    void checkNicknameDuplicate_test() {
        // given
        memberMapper.save(Member.of(1L, "nickname", "image"));

        // when
        MemberNicknameDuplicateResponse response = memberService.checkNicknameDuplicate("nickname");

        // then
        assertThat(response.isDuplicate()).isTrue();
    }

    @Test
    @DisplayName("사용자 프로필 설정")
    void editMemberProfile_test() {
        // given
        String nickname = "nickname";
        String profileImage = "profile";
        String baekjoonId = "baek";

        EditMemberProfileRequest request = new EditMemberProfileRequest(nickname, profileImage, baekjoonId);
        Member newMember = Member.create(1L);
        memberMapper.save(newMember);

        // when
        memberService.editMemberProfile(newMember.getMemberId(), request);

        // then
        Member member = memberMapper.findById(newMember.getMemberId()).orElse(null);
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getProfileImage()).isEqualTo(profileImage);
        assertThat(member.getBaekjoonId()).isEqualTo(baekjoonId);
        assertThat(member.getMemberStatus()).isEqualTo(ACTIVE);
    }

}
