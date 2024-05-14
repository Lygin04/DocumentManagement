package com.lygin.documentmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "redirect:/document/getAll";
    }

    @GetMapping("/create")
    public String create() {
        return "create";
    }
}
