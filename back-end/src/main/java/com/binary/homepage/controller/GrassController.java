package com.binary.homepage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/grass")
public class GrassController {

    @GetMapping("/list")
    public String grass() {
        return("grass");
    }
}
