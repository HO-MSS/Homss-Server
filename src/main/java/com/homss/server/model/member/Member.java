package com.homss.server.model.member;

import com.homss.server.common.modle.TimeModel;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("member")
public class Member extends TimeModel {

    private Long memberId;
    private Long socialId;
    private String nickname;
    private String profileImage;
    private String baekjoonId;
    private Integer solveCount;
    private Long score;
    private MemberStatus memberStatus;
    private String refreshToken;

    public static Member of(Long socialId, String nickname, String profileImage) {
        return Member.builder()
                .socialId(socialId)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }

    public static Member create(Long socialId) {
        return Member.builder()
                .socialId(socialId)
                .build();
    }

    public void changeNickname(String nickname) {
        this.nickname=nickname;
    }

    public void changeProfileImage(String profileImage) {
        this.profileImage=profileImage;
    }

    public void changeBaekjoonId(String baekjoonId) {
        this.baekjoonId=baekjoonId;
    }

    public void changeMemberStatus(MemberStatus memberStatus) {
        this.memberStatus=memberStatus;
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken=refreshToken;
    }

}
