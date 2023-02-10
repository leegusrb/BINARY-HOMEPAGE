package com.binary.homepage.controller;

import com.binary.homepage.domain.Notice;
import com.binary.homepage.repository.NoticeRepository;
import com.binary.homepage.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;

    @GetMapping("/list")
    public String notice(Model model) {
        List<Notice> notices = noticeService.findAll();
        model.addAttribute("notices", notices);
        return "notice";
    }

    @GetMapping("/write")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("notice", new Notice());
        } else {
            Notice notice = noticeRepository.findById(id).orElse(null);
            model.addAttribute("notice", notice);
        }

        return "write";
    }

    @PostMapping("/write")
    public String formSubmit(@Valid Notice notice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "write";
        }
        noticeService.save(notice);
        return "redirect:/notice/list";
    }
}
