package com.homss.server.dto.response;

import com.homss.server.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberMypageReponse {

    private Long memberId;
    private String nickname;
    private String profileImage;
    private String baekjoonId;
    private Integer solveCount;
    private Long score;

    public static MemberMypageReponse from(Member member) {
        return MemberMypageReponse.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .baekjoonId(member.getBaekjoonId())
                .solveCount(member.getSolveCount())
                .score(member.getScore())
                .build();
    }

}
