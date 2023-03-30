package com.binary.homepage.repository;

import com.binary.homepage.domain.board.Board;
import com.binary.homepage.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoard(Board board);
}
