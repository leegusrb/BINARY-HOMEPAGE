package com.binary.homepage.service;

import com.binary.homepage.domain.Grass;
import com.binary.homepage.domain.GrassInfo;
import com.binary.homepage.repository.GrassJpaRepository;
import com.binary.homepage.repository.GrassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GrassService {

    private final GrassRepository grassRepository;
    private final GrassJpaRepository grassJpaRepository;

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
    public Grass findOne(Long grassId) {
        return grassRepository.findOne(grassId);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void updateGrass() {

    }

    public List<GrassInfo> getMonthGrass(Grass grass) {
        LocalDate start = LocalDate.now().plusDays(-30);
        return grassJpaRepository.findAllByGrassEqualsAndDateIsAfter(grass, start);
    }
}
