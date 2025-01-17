package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.PasswordEncoder;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @GetMapping("/")
    public String home(HttpSession session, Model model) {

      
        return "/index";
    }

    @GetMapping("/formlogin")
    public String formlogin() {
        return "/formlogin";
    }
    
    @PostMapping("/login")
    public String login() {


        return "/formlogin";
    }
    



    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}
