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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    String[] boardName = {"notice", "study", "project", "money", "qna"};
    String[] boardTitle = {"공지사항", "같이 공부해요", "같이 만들어요", "회비 현황", "Q&A"};
    String[] boardContent = {"BINARY 규칙 안내 및 주요 전달 사항을 공지하는 게시판입니다.", "", "", "", "", ""};

    public HashMap<String, String> getBoardTitle() {
        HashMap<String, String> boardTitleMap = new HashMap<>();
        for (int i = 0; i < 5; i++)
            boardTitleMap.put(boardName[i], boardTitle[i]);

        return boardTitleMap;
    }

    public HashMap<String, String> getBoardContent() {
        HashMap<String, String> boardContentMap = new HashMap<>();
        for (int i = 0; i < 5; i++)
            boardContentMap.put(boardName[i], boardContent[i]);

        return boardContentMap;
    }

    public List<Board> findAll() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "time"));
    }

    public List<Board> findAllByMember(Member member) {
        return boardRepository.findAllByMemberOrderByTimeDesc(member);
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
