package com.binary.homepage.controller;

import com.binary.homepage.domain.Member;
import com.binary.homepage.domain.board.Board;
import com.binary.homepage.domain.board.Comment;
import com.binary.homepage.repository.BoardRepository;
import com.binary.homepage.repository.CommentRepository;
import com.binary.homepage.service.BoardService;
import com.binary.homepage.service.CommentService;
import com.binary.homepage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @GetMapping("/{type}")
    public String noticeList(@PathVariable String type, Model model, @PageableDefault(size = 10) Pageable pageable, Principal principal) {
        Page<Board> boards = boardService.findAllByType(type, pageable);
        int currentPage = boards.getPageable().getPageNumber();
        int startPage = Math.max(0, boards.getPageable().getPageNumber() - 2);
        int endPage = Math.min(boards.getTotalPages() - 1, boards.getPageable().getPageNumber() + 2);
        int id = 0;
        if (principal != null) id = Integer.parseInt(principal.getName());
        Member member = memberService.findOne(id);
        List<Board> allBoard = boardService.findAll();
        int end = Math.min(allBoard.size(), 3);
        List<Board> boardList = allBoard.subList(0, end);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        model.addAttribute("service", boardService);
        model.addAttribute("type", type);
        model.addAttribute("studentId", id);
        model.addAttribute("boardList", boardList);
        model.addAttribute("boardTitle", boardService.getBoardTitle().get(type));
        model.addAttribute("boardContent", boardService.getBoardContent().get(type));
        return "board/list";
    }

    @GetMapping("/{type}/view/{id}")
    @Transactional
    public String boardView(@PathVariable String type, @PathVariable Long id, Model model, Principal principal) {
        Board board = boardService.findOne(id);
        board.increaseView();
        int currentId = 0;
        if (principal != null) currentId = Integer.parseInt(principal.getName());
        CommentForm commentForm = new CommentForm();
        commentForm.setStudentId(currentId);
        List<Comment> comments = commentRepository.findAllByBoard(board);
        List<Board> allBoard = boardService.findAll();
        int end = Math.min(allBoard.size(), 3);
        List<Board> boardList = allBoard.subList(0, end);
        Board prev = boardRepository.findByNumAndType(board.getNum() - 1, board.getType());
        Board next = boardRepository.findByNumAndType(board.getNum() + 1, board.getType());

        model.addAttribute("board", board);
        model.addAttribute("currentId", currentId);
        model.addAttribute("type", type);
        model.addAttribute("commentForm", commentForm);
        model.addAttribute("comments", comments);
        model.addAttribute("service", boardService);
        model.addAttribute("boardList", boardList);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        return "board/view";
    }

    @GetMapping("/{type}/write")
    public String boardWrite(@PathVariable String type, Model model, Principal principal) {
        int currentId = Integer.parseInt(principal.getName());
        BoardForm boardForm = new BoardForm();
        boardForm.setMember(memberService.findOne(currentId));

        model.addAttribute("id", 0);
        model.addAttribute("board", boardForm);
        model.addAttribute("type", type);
        model.addAttribute("currentId", currentId);
        return "board/write";
    }

    @PostMapping("/{type}/write/0")
    public String submitWrite(@PathVariable String type, @ModelAttribute BoardForm boardForm) {
        Board board = boardService.createBoard(type, boardForm.getTitle(), boardForm.getContent(), boardForm.getMember());
        boardService.save(board);
        return "redirect:/" + type;
    }

    @GetMapping("/{type}/write/{id}")
    public String boardEdit(@PathVariable String type, @PathVariable Long id, Model model, Principal principal) {
        int currentId = Integer.parseInt(principal.getName());
        Board board = boardService.findOne(id);
        BoardForm boardForm = new BoardForm();
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        boardForm.setMember(board.getMember());

        model.addAttribute("id", id);
        model.addAttribute("board", boardForm);
        model.addAttribute("type", type);
        model.addAttribute("currentId", currentId);
        return "board/write";
    }

    @PostMapping("/{type}/write/{id}")
    @Transactional
    public String submitEdit(@PathVariable String type, @PathVariable Long id, @ModelAttribute BoardForm boardForm) {
        Board board = boardService.findOne(id);
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        return "redirect:/" + type;
    }

    @GetMapping("/{type}/delete/{id}")
    @Transactional
    public String delete(@PathVariable String type, @PathVariable Long id) {
        Board board = boardService.findOne(id);
        board.setEnable(false);
        return "redirect:/" + type;
    }

    @PostMapping("/{type}/comment/{id}")
    public String comment(@PathVariable String type, @PathVariable Long id, @ModelAttribute CommentForm commentForm) {
        if (commentForm.getStudentId() == 0) return "redirect:/login";
        Board board = boardService.findOne(id);
        Member member = memberService.findOne(commentForm.getStudentId());
        Comment comment = Comment.createComment(commentForm.getComment(), board, member);
        commentService.save(comment);
        return "redirect:/" + type + "/view/" + id;
    }
}
