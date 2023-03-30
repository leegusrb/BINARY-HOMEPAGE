package com.binary.homepage.repository;

import com.binary.homepage.domain.Member;
import com.binary.homepage.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByEnableTrueAndTypeOrderByTimeDesc(String type, Pageable pageable);

    List<Board> findAllByType(String type);

    Board findByNumAndType(int num, String type);

    List<Board> findAllByMemberOrderByTimeDesc(Member member);
}
