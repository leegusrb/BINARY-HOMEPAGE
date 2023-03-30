package com.binary.homepage.controller;

import com.binary.homepage.domain.GrassInfo;
import com.binary.homepage.domain.Member;
import com.binary.homepage.domain.board.Board;
import com.binary.homepage.service.BoardService;
import com.binary.homepage.service.GrassService;
import com.binary.homepage.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final GrassService grassService;
    private final BoardService boardService;

    @InitBinder("passwordForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordFormValidator());
    }

    @GetMapping("/{studentId}")
    public String myPage(@PathVariable int studentId, Model model, Principal principal) {
        Member member = memberService.findOne(studentId);
        List<GrassInfo> grass = grassService.getMonthGrass(member.getGrass());
        int currentId = 0;
        if (principal != null) currentId = Integer.parseInt(principal.getName());
        List<Board> allBoards = boardService.findAllByMember(member);
        int end = Math.min(allBoards.size(), 3);
        List<Board> boards = allBoards.subList(0, end);

        model.addAttribute("member", member);
        model.addAttribute("grass", grass);
        model.addAttribute("currentId", currentId);
        model.addAttribute("boards", boards);
        return "myPage";
    }

    @GetMapping("/{studentId}/updateInfo")
    public String updateInfoForm(@PathVariable int studentId, Model model, Principal principal) {
        if (principal == null || studentId != Integer.parseInt(principal.getName()))
            return "redirect:/myPage/" + studentId;

        Member member = memberService.findOne(studentId);

        MemberForm memberForm = new MemberForm();
        memberForm.setId(member.getId());
        memberForm.setIntroduce(member.getIntroduce());
        if (member.getGrass() != null) memberForm.setGrassName(member.getGrass().getGrassName());
        memberForm.setGitHub(member.getGitHub());
        memberForm.setNotion(member.getNotion());

        model.addAttribute("memberForm", memberForm);
        model.addAttribute("passwordForm", new PasswordForm());
        return "updateInfo";
    }

    @PostMapping("/{studentId}/updateInfo")
    public String updateInfo(@PathVariable int studentId, @ModelAttribute("memberForm") MemberForm memberForm) {
        memberService.updateInfo(memberForm.getId(), memberForm.getIntroduce(), memberForm.getGrassName(), memberForm.getGitHub(), memberForm.getNotion());

        return "redirect:/myPage/" + studentId;
    }

    @PostMapping("/{studentId}/updatePassword")
    public String updatePassword(@PathVariable int studentId, @Valid PasswordForm passwordForm, Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/myPage/" + studentId + "/updateInfo";
        }
        memberService.updatePassword(memberService.findOne(studentId), passwordForm.getNewPassword());
        return "redirect:/myPage/" + studentId;
    }
}
