package com.homss.server.service;

import com.homss.server.common.oauth.KakaoClient;
import com.homss.server.common.jwt.JwtProvider;
import com.homss.server.dto.etc.MemberJwtTokens;
import com.homss.server.dto.request.SocialLoginRequest;
import com.homss.server.dto.response.SocialLoginResponse;
import com.homss.server.mapper.MemberMapper;
import com.homss.server.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoClient kakaoClient;
    private final JwtProvider jwtProvider;
    private final MemberMapper memberMapper;

    @Transactional
    public SocialLoginResponse login(SocialLoginRequest request) {
        Long socialId = kakaoClient.getMemberSocialId(request.socialAccessToken());
        Member member = getMember(socialId);
        MemberJwtTokens memberJwtTokens = getMemberJwtTokens(member);
        return SocialLoginResponse.of(memberJwtTokens);
    }

    private Member getMember(Long socialId) {
        Member member = memberMapper.findBySocialId(socialId).orElse(null);
        if (member == null) {
            member = Member.create(socialId);
            memberMapper.save(member);
        }
        return member;
    }

    private MemberJwtTokens getMemberJwtTokens(Member member) {
        String accessToken = jwtProvider.createAccessToken(member.getMemberId());
        String refreshToken = jwtProvider.createRefreshToken(member.getMemberId());
        return new MemberJwtTokens(accessToken, refreshToken) ;
    }

}
