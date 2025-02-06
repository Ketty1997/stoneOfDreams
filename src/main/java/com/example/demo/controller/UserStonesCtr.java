package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.UserStoneDto;
import com.example.demo.model.Stone;
import com.example.demo.model.UserStone;
import com.example.demo.services.UserStonesService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("userStones")
public class UserStonesCtr {

    @Autowired
    private UserStonesService userStonesService;

    @GetMapping({"", "/"})
    public String allUserStones(Model model, HttpSession session) {

        model.addAttribute("listaPietreUtente", userStonesService.listaPietreUtente((Integer)session.getAttribute("user")));

        return "userStones";
    }
    


}
