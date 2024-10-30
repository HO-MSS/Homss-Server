package com.homss.server.mapper;

import com.homss.server.ServerApplicationTests;
import com.homss.server.model.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
}
