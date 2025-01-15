package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.util.PasswordEncoder;

import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @GetMapping("/")
    public String home(HttpSession session, Model model) {



        return "/index";
    }
    


}
