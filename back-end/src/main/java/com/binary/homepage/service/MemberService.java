package com.binary.homepage.service;

import com.binary.homepage.component.Crawling;
import com.binary.homepage.domain.Grass;
import com.binary.homepage.domain.Member;
import com.binary.homepage.domain.Role;
import com.binary.homepage.repository.GrassInfoRepository;
import com.binary.homepage.repository.GrassRepository;
import com.binary.homepage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final GrassRepository grassRepository;
    private final GrassService grassService;
    private final Crawling crawling;
    private final GrassInfoRepository grassInfoRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 1명 조회
     */
    public Member findOne(int studentId) {
        return memberRepository.findByStudentId(studentId);
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    public void updatePassword(Member member, String newPassword) {
        member.updatePassword(passwordEncoder.encode(newPassword));
    }

    /**
     * 상세정보 변경
     */
    @Transactional
    public void updateInfo(Long id, String introduce, String grassName, String gitHub) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.setIntroduce(introduce);
        member.setGitHub(gitHub);
        if (member.getGrass().getGrassName() == null) {
            member.getGrass().setGrassName(grassName);
            grassService.initGrass(member.getGrass(), grassName);
        }
        else if (!member.getGrass().getGrassName().equals(grassName)) {
            grassInfoRepository.deleteAll(grassInfoRepository.findAllByGrass(member.getGrass()));
            member.getGrass().setGrassName(grassName);
            grassService.initGrass(member.getGrass(), grassName);
        }
    }


    public Member createMember(int studentId, String name, int generation, String password) {
        Member member = new Member();
        member.setStudentId(studentId);
        member.setName(name);
        member.setGeneration(generation);
        member.setPassword(passwordEncoder.encode(password));
        member.setWarningNum(0);
        member.setRole(Role.MEMBER);
        member.setEnabled(true);
        Grass grass = new Grass();
        grass.setMember(member);
        member.setGrass(grass);
        return member;
    }
}
