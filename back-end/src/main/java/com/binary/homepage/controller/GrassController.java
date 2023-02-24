package com.binary.homepage.controller;

import com.binary.homepage.domain.Grass;
import com.binary.homepage.service.GrassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/grass")
@RequiredArgsConstructor
public class GrassController {

    private final GrassService grassService;

    @GetMapping("")
    public String grass(Model model) {
        List<Grass> ranking = grassService.findRanking();
        model.addAttribute("ranking", ranking);
        model.addAttribute("service", grassService);
        return("grass");
    }
}
