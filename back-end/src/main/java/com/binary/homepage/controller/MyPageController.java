package com.binary.homepage.controller;

import com.binary.homepage.domain.GrassInfo;
import com.binary.homepage.domain.Member;
import com.binary.homepage.service.GrassService;
import com.binary.homepage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final GrassService grassService;

    @GetMapping("/{studentId}")
    public String myPage(@PathVariable int studentId, Model model) {
        Member member = memberService.findOne(studentId);
        List<GrassInfo> grass = grassService.getMonthGrass(member.getGrass());
        model.addAttribute("member", member);
        model.addAttribute("grass", grass);
        return "myPage";
    }

    @GetMapping("/{studentId}/updateInfo")
    public String updateInfoForm(@PathVariable int studentId, Model model) {
        Member member = memberService.findOne(studentId);

        MemberForm memberForm = new MemberForm();
        memberForm.setId(member.getId());
        memberForm.setIntroduce(member.getIntroduce());
        memberForm.setGrassName(member.getGrass().getGrassName());
        memberForm.setGitHub(member.getGitHub());

        model.addAttribute("memberForm", memberForm);
        return "updateInfo";
    }

    @PostMapping("/{studentId}/updateInfo")
    public String updateInfo(@PathVariable Long studentId, @ModelAttribute("memberForm") MemberForm memberForm) {
        memberService.updateInfo(memberForm.getId(), memberForm.getIntroduce(), memberForm.getGrassName(), memberForm.getGitHub());

        return "redirect:/" + studentId;
    }
}
