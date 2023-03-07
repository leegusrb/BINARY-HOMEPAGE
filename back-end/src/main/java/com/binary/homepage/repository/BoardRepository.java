package com.binary.homepage.repository;

import com.binary.homepage.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByTypeOrderByTimeDesc(String type, Pageable pageable);
}
