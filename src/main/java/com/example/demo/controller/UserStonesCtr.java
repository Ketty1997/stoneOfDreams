package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserStoneDto;
import com.example.demo.model.Stone;
import com.example.demo.model.User;
import com.example.demo.model.UserStone;
import com.example.demo.services.UserService;
import com.example.demo.services.UserStonesService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/userStones")
public class UserStonesCtr {

    @Autowired
    private UserStonesService userStonesService;

    @Autowired
    private UserService userService;
    
   

    @GetMapping({"", "/"})
    public String allUserStones(Model model, HttpSession session) {

        User utente = userService.getUserFromSession(session);

        model.addAttribute("listaPietreUtente", userStonesService.listaPietreUtente((Integer)session.getAttribute("user")));
        model.addAttribute("utente", utente.getNome());

        return "userStones";
    }
    

    @GetMapping("/delete")
    public String delStone(@RequestParam int id) {

        System.out.println("pietra con id: " + id);

        userStonesService.deleteUserStone(id);

        return "redirect:/userStones";
    }
    

    @GetMapping("/addToColl")
    public String addToCollection(@RequestParam int id, Model model, HttpSession session) {

        User utente = userService.getUserFromSession(session);

        //per l'id utente da mettere nella colonna
        int idUtente = utente.getId();

        //per data attuale
        LocalDate data =LocalDate.now();
        DateTimeFormatter formattazione = DateTimeFormatter.ofPattern("yyyy.MM-dd");
        String dataFormattata = data.format(formattazione);
    


        return "/userStones";
    }
    


}
