package com.binary.homepage.repository;

import com.binary.homepage.domain.Grass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface GrassRepository extends JpaRepository<Grass, Long> {
}
