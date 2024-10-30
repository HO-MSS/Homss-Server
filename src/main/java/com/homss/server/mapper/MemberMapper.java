package com.homss.server.mapper;

import com.homss.server.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    void save(Member member);
    List<Member> findAll();
    void deleteAll();
    Member findById(Long id);

}
