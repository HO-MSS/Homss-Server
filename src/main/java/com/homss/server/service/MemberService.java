package com.homss.server.service;

import com.homss.server.dto.response.MemberNicknameDuplicateResponse;
import com.homss.server.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional(readOnly = true)
    public MemberNicknameDuplicateResponse checkNicknameDuplicate(String nickname) {
        boolean isDuplicate = memberMapper.checkNicknameDuplicate(nickname);
        return new MemberNicknameDuplicateResponse(isDuplicate);
    }

}
