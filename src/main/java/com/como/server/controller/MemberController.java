package com.como.server.controller;

import com.como.server.model.Member;
import com.como.server.service.MemberService;
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
