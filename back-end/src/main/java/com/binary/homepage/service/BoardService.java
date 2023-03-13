package com.binary.homepage.service;

import com.binary.homepage.domain.Member;
import com.binary.homepage.domain.board.Board;
import com.binary.homepage.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAll() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "time"));
    }

    public Page<Board> findAllByType(String type, Pageable pageable) {
        return boardRepository.findAllByEnableTrueAndTypeOrderByTimeDesc(type, pageable);
    }

    public void save(Board board) {
        boardRepository.save(board);
    }

    public Board findOne(Long id) {
        return boardRepository.findById(id).orElseThrow();
    }

    public Board createBoard(String type, String title, String content, Member member) {
        Board board = new Board();
        board.setType(type);
        board.setTitle(title);
        board.setContent(content);
        board.setMember(member);
        board.setTime(LocalDateTime.now());
        board.setView(0L);
        board.setEnable(true);
        board.setNum(boardRepository.findAllByType(type).size() + 1);
        return board;
    }

    public String localDateTimeToString(LocalDateTime time) {
        return time.format(
                DateTimeFormatter.ofPattern("MM/dd HH:mm")
        );
    }
}
