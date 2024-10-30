package com.como.server.model;

import com.como.server.common.modle.TimeModel;
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
    private Integer solveCount;
    private Long score;

    public static Member of(Long socialId, String nickname, String profileImage) {
        return Member.builder()
                .socialId(socialId)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }

}
