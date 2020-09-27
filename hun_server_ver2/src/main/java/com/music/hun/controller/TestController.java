package com.music.hun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("name", "sohyun");

        List<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("banana");

        model.addAttribute("list", fruits);

        return "test";
    }
}
