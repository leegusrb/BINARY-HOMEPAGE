package com.binary.homepage.repository;

import com.binary.homepage.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByStudentId(int studentId);
}
