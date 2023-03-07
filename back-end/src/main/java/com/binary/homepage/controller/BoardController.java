package com.binary.homepage.controller;

import com.binary.homepage.domain.board.Board;
import com.binary.homepage.service.BoardService;
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

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("")
    public String noticeList(Model model, @PageableDefault(size = 8) Pageable pageable) {
        Page<Board> boards = boardService.findAll(pageable);
        int currentPage = boards.getPageable().getPageNumber();
        int startPage = Math.max(0, boards.getPageable().getPageNumber() - 2);
        int endPage = Math.min(boards.getTotalPages() - 1, boards.getPageable().getPageNumber() + 2);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/view/{id}")
    @Transactional
    public String boardView(@PathVariable Long id, Model model) {
        Board board = boardService.findOne(id);

        board.increaseView();

        model.addAttribute("board", board);
        return "board/view";
    }

    @GetMapping("/write")
    public String boardWrite(Model model, Principal principal) {
        int currentId = Integer.parseInt(principal.getName());
        BoardForm boardForm = new BoardForm();
        boardForm.setMember(memberService.findOne(currentId));
        model.addAttribute("board", boardForm);
        return "board/write";
    }

    @PostMapping("/write")
    public String submitWrite(@ModelAttribute BoardForm boardForm) {
        Board board = Board.createBoard("notice", boardForm.getTitle(), boardForm.getContent(), boardForm.getMember());
        boardService.save(board);
        return "redirect:/board";
    }

    @GetMapping("/write/{id}")
    public String boardEdit(@PathVariable Long id, Model model, Principal principal) {
        int currentId = Integer.parseInt(principal.getName());
        Board board = boardService.findOne(id);
        BoardForm boardForm = new BoardForm();
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        boardForm.setMember(board.getMember());
        model.addAttribute("board", boardForm);
        return "board/write";
    }

    @PostMapping("/write/{id}")
    public String submitEdit(@PathVariable Long id, @ModelAttribute BoardForm boardForm) {
        Board board = Board.createBoard("notice", boardForm.getTitle(), boardForm.getContent(), boardForm.getMember());
        boardService.save(board);
        return "redirect:/board";
    }

}
