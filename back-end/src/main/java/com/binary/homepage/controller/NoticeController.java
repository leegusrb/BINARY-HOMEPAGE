package com.binary.homepage.controller;

import com.binary.homepage.domain.board.Notice;
import com.binary.homepage.service.NoticeService;
import lombok.RequiredArgsConstructor;
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
    public String noticeList(Model model) {
        List<Notice> notices = noticeService.findAll();
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
