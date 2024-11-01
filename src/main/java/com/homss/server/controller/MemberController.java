package com.homss.server.controller;

import com.homss.server.dto.request.EditMemberProfileRequest;
import com.homss.server.dto.response.MemberNicknameDuplicateResponse;
import com.homss.server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/duplicate/{nickname}")
    public ResponseEntity<MemberNicknameDuplicateResponse> checkNicknameDuplicate(@PathVariable String nickname) {
        MemberNicknameDuplicateResponse response = memberService.checkNicknameDuplicate(nickname);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<Void> editMemberProfile(//@CurrentMemberId Long memberId,
                                                  @RequestBody EditMemberProfileRequest request) {
        memberService.editMemberProfile(1L, request);
        return ResponseEntity.ok().build();
    }

}
