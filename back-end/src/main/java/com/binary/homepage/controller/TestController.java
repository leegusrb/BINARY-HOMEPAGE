//package com.binary.homepage.controller;
//
//import com.binary.homepage.domain.Member;
//import com.binary.homepage.repository.MemberRepository;
//import com.binary.homepage.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//@RequiredArgsConstructor
//public class TestController {
//
//    private final MemberService memberService;
//
//    @GetMapping("/test")
//    public String test() {
//        return "test";
//    }
//
//    @GetMapping("/change")
//    public String change() {
//        Member member = memberService.findOne(23011855);
//        memberService.updatePassword(member, "01028871062");
//
//        return "redirect: /test";
//    }
//}
