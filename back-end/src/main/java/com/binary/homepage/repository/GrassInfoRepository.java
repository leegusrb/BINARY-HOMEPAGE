package com.binary.homepage.repository;

import com.binary.homepage.domain.Grass;
import com.binary.homepage.domain.GrassInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GrassInfoRepository extends JpaRepository<GrassInfo, Long> {

    List<GrassInfo> findAllByGrassEqualsAndDateIsAfterAndDateIsBefore(Grass grass, LocalDate start, LocalDate end);

    List<GrassInfo> findAllByGrass(Grass grass);
}
