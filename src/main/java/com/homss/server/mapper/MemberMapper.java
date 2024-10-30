package com.homss.server.mapper;

import com.homss.server.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void save(Member member);
    Member findById(Long id);

}
