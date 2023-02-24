package com.binary.homepage.controller;

import com.binary.homepage.domain.Grass;
import com.binary.homepage.service.GrassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final GrassService grassService;

    @GetMapping("/")
    public String home(Principal principal, Model model) {
        List<Grass> ranking = grassService.findRanking();
        model.addAttribute("ranking", ranking);
        model.addAttribute("service", grassService);
        if (principal != null) {
            model.addAttribute("studentId", principal.getName());
        }
        return "index";
    }

}
