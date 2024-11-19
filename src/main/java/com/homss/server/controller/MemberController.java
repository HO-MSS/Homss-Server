package com.homss.server.controller;

import com.homss.server.common.annotation.CurrentMemberId;
import com.homss.server.dto.request.EditMemberProfileRequest;
import com.homss.server.dto.response.MemberMypageReponse;
import com.homss.server.dto.response.MemberNicknameAvailability;
import com.homss.server.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/availability/{nickname}")
    public ResponseEntity<MemberNicknameAvailability> checkNicknameAvailability(@PathVariable String nickname) {
        MemberNicknameAvailability response = memberService.checkNicknameAvailability(nickname);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/mypage")
        public ResponseEntity<MemberMypageReponse> getMemberMypage(@CurrentMemberId Long memberId) {
        MemberMypageReponse response = memberService.getMemberMypage(memberId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<Void> editMemberProfile(@CurrentMemberId Long memberId,
                                                  @Valid @RequestBody EditMemberProfileRequest request) {
        memberService.editMemberProfile(memberId, request);
        return ResponseEntity.ok().build();
    }

}
