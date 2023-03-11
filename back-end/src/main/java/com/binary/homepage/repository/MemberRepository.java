package com.binary.homepage.repository;

import com.binary.homepage.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByStudentId(int studentId);
}
