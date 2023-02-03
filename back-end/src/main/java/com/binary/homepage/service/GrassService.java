package com.binary.homepage.service;

import com.binary.homepage.domain.Grass;
import com.binary.homepage.repository.GrassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GrassService {

    private final GrassRepository grassRepository;
    private final PasswordEncoder passwordEncoder;

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
                .sorted((g1, g2) -> g2.grassNum() - g1.grassNum())
                .collect(Collectors.toList());
    }

    /**
     * 잔디심기 1명 조회
     */
    public Grass findOne(Long grassId) {
        return grassRepository.findOne(grassId);
    }
}
