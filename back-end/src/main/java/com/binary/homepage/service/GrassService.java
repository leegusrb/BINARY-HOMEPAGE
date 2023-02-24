package com.binary.homepage.service;

import com.binary.homepage.component.Crawling;
import com.binary.homepage.domain.Grass;
import com.binary.homepage.domain.GrassInfo;
import com.binary.homepage.domain.Member;
import com.binary.homepage.repository.GrassInfoRepository;
import com.binary.homepage.repository.GrassRepository;
import com.binary.homepage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GrassService {

    private final GrassRepository grassRepository;
    private final GrassInfoRepository grassInfoRepository;
    private final Crawling crawling;
    private final MemberRepository memberRepository;

    /**
     * 잔디심기 전체 조회
     */
    public List<Grass> findAll() {
        return grassRepository.findAll();
    }

    /**
     * 잔디심기 순위 조회
     */
    public List<Grass> findRanking() {
        List<Grass> grasses = grassRepository.findAll();

        return grasses
                .stream()
                .sorted((g1, g2) -> getMonthGrass(g2).size() - getMonthGrass(g1).size())
                .collect(Collectors.toList());
    }

    /**
     * 잔디심기 1명 조회
     */
    public Optional<Grass> findOne(Long grassId) {
        return grassRepository.findById(grassId);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void updateGrass() {
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            Grass grass = member.getGrass();
            if (grass != null) {
                GrassInfo todayGrassInfo = crawling.getTodayGrassData(grass.getGrassName());
                grass.addGrassInfo(todayGrassInfo);
            }
        }
    }

    public List<GrassInfo> getMonthGrass(Grass grass) {
        LocalDate start = LocalDate.now().plusDays(-31);
        return grassInfoRepository.findAllByGrassEqualsAndDateIsAfter(grass, start);
    }

    @Transactional
    public void initGrass(Member member, String grassName) {
        List<GrassInfo> grassInfos = crawling.listGrassInfo(grassName);
        Grass grass = Grass.createGrass(member, grassName, grassInfos);
        grassRepository.save(grass);
    }
}
