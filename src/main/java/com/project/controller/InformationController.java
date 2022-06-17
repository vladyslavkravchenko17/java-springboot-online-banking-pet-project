package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class InformationController {

    @GetMapping("credit")
    public String creditRules() {
        return "info/creditRules";
    }

    @GetMapping("deposit")
    public String depositRules() {
        return "info/depositRules";
    }
}
