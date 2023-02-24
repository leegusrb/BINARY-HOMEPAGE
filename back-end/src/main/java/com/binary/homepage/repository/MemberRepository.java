package com.binary.homepage.repository;

import com.binary.homepage.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByStudentId(int studentId);
}
