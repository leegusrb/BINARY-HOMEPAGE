package com.binary.homepage.controller;

import com.binary.homepage.domain.GrassInfo;
import com.binary.homepage.domain.Member;
import com.binary.homepage.service.GrassService;
import com.binary.homepage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final GrassService grassService;

    @GetMapping("/{studentId}")
    public String myPage(Model model, @PathVariable int studentId) {
        Member member = memberService.findOne(studentId);
        List<GrassInfo> grass = grassService.getMonthGrass(member.getGrass());
        model.addAttribute("member", member);
        model.addAttribute("grass", grass);
        return "myPage";
    }
}
