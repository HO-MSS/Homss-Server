package com.homss.server.controller;

import com.homss.server.model.member.Member;
import com.homss.server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<Member> saveMember() {
        Member member = memberService.saveMember();
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/{memberId}")
    public Member findMember(@PathVariable Long memberId) {
        return memberService.findMemberById(memberId);
    }

}
