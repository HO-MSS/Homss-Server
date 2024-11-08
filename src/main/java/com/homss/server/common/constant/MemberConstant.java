package com.homss.server.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberConstant {
    DELETE_MEMBER_NICKNAME("탈퇴회원"),
    BAN_MEMBER_NICKNAME("정지회원");

    private final String nickname;
}
