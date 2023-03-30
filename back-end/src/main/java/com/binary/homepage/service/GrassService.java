package com.binary.homepage.service;

import com.binary.homepage.component.Crawling;
import com.binary.homepage.domain.Grass;
import com.binary.homepage.domain.GrassInfo;
import com.binary.homepage.domain.Member;
import com.binary.homepage.repository.GrassInfoRepository;
import com.binary.homepage.repository.GrassRepository;
import com.binary.homepage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@EnableScheduling
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
        List<Grass> grasses = findAll();

        return grasses
                .stream()
                .sorted((g1, g2) -> getMonthGrass(g2).size() - getMonthGrass(g1).size())
                .collect(Collectors.toList());
    }

    public List<GrassInfo> getMonthGrassInfos(Grass grass, int month) {
        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of(now.getYear(), Month.of(month), 1);
        LocalDate end = LocalDate.of(now.getYear(), Month.of(month + 1), 1).plusDays(-1);
        return grassInfoRepository.findAllByGrassEqualsAndDateIsAfterAndDateIsBefore(grass, start, end);
    }

    public List<Grass> findMonthRanking(List<Grass> grasses, int month) {
        return grasses
                .stream()
                .sorted((g1, g2) -> getMonthGrassInfos(g2, month).size() - getMonthGrassInfos(g1, month).size())
                .collect(Collectors.toList());
    }

    /**
     * 잔디심기 1명 조회
     */
    public Optional<Grass> findOne(Long grassId) {
        return grassRepository.findById(grassId);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateGrass() {
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            Grass grass = member.getGrass();
            if (grass.getGrassName() == null || grass.getGrassName().isEmpty()) continue;
            GrassInfo todayGrassInfo = crawling.getTodayGrassData(grass.getGrassName());
            if (todayGrassInfo != null) {
                grass.addGrassInfo(todayGrassInfo);
                grassInfoRepository.save(todayGrassInfo);
            }
        }
    }

    public List<GrassInfo> getMonthGrass(Grass grass) {
        LocalDate start = LocalDate.now().plusDays(-31);
        LocalDate end = LocalDate.now();
        return grassInfoRepository.findAllByGrassEqualsAndDateIsAfterAndDateIsBefore(grass, start, end);
    }

    @Transactional
    public void initGrass(Grass grass, String grassName) {
        List<GrassInfo> grassInfos = crawling.listGrassInfo(grassName);
        if (grassInfos == null) return;
        for (GrassInfo grassInfo :
                grassInfos) {
            grassInfo.setGrass(grass);
        }
        grass.setGrassInfos(grassInfos);
        grassInfoRepository.saveAll(grassInfos);
    }
}
