package com.binary.homepage.controller;

import com.binary.homepage.domain.board.Notice;
import com.binary.homepage.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public String noticeList(Model model, @PageableDefault(size = 8)Pageable pageable) {
        Page<Notice> notices = noticeService.findAll(pageable);
        int currentPage = notices.getPageable().getPageNumber();
        int startPage = Math.max(0, notices.getPageable().getPageNumber() - 2);
        int endPage = Math.min(notices.getTotalPages() - 1, notices.getPageable().getPageNumber() + 2);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("notices", notices);
        return "notice/list";
    }

    @GetMapping("/view/{id}")
    @Transactional
    public String noticeView(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findOne(id);

        notice.increaseView();

        model.addAttribute("notice", notice);
        return "notice/view";
    }

    @GetMapping("/write/{id}")
    public String noticeWrite(@PathVariable Long id) {
        return "notice/write";
    }
}
