package com.demente.ideas.learnwords.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 1987diegog
 */
@Controller
public class HomeController {

    private static final String appName = "learn-words";

    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(value = "name", required = false,
                               defaultValue = "Guest") String name) {
        model.addAttribute("name", name);
        model.addAttribute("title", appName);
        return "home";
    }
}
