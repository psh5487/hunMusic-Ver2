package com.music.hun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("name", "sohyun");

        List<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("banana");

        model.addAttribute("list", fruits);
        return "main";
    }
}
