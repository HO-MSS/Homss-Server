package com.homss.server.service;

import com.homss.server.common.constant.MemberConstant;
import com.homss.server.common.exception.ApplicationException;
import com.homss.server.dto.request.EditMemberProfileRequest;
import com.homss.server.dto.response.MemberMypageReponse;
import com.homss.server.dto.response.MemberNicknameAvailability;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.member.Member;
import com.homss.server.model.member.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.homss.server.common.exception.ExceptionCode.MEMBER_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional(readOnly = true)
    public MemberNicknameAvailability checkNicknameAvailability(String nickname) {
        String deleteNickname = MemberConstant.DELETE_MEMBER_NICKNAME.getNickname();
        String banNickname = MemberConstant.BAN_MEMBER_NICKNAME.getNickname();

        if (nickname.equals(deleteNickname) || nickname.equals(banNickname)) {
            new MemberNicknameAvailability(false);
        }

        boolean isDuplicate = memberMapper.checkNicknameDuplicate(nickname);
        return new MemberNicknameAvailability(isDuplicate);
    }

    @Transactional
    public void editMemberProfile(Long memberId, EditMemberProfileRequest request) {
        Member member = memberMapper.findById(memberId)
                .orElseThrow(() -> ApplicationException.create(MEMBER_NOT_FOUND_ERROR));

        member.changeNickname(request.nickname());
        member.changeProfileImage(request.profileImage());
        member.changeBaekjoonId(request.baekjoonId());
        member.changeMemberStatus(MemberStatus.ACTIVE);

        memberMapper.changeMemberProfile(member);
    }

    @Transactional(readOnly = true)
    public MemberMypageReponse getMemberMypage(Long memberId) {
        Member member = memberMapper.findById(memberId)
                .orElseThrow(() -> ApplicationException.create(MEMBER_NOT_FOUND_ERROR));
        return MemberMypageReponse.from(member);
    }

}
