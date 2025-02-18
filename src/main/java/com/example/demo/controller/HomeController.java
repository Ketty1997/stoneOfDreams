package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.PasswordEncoder;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @GetMapping("/")
    public String home(HttpSession session, Model model) {

         
        if(session.getAttribute("user") != null) {

            //prendo l'utente attraverso l'id nella sessione, estraggo l'immagine e lo passo al model
            User user = userRepository.findById((Integer) session.getAttribute("user")).get();
            // model.addAttribute("immUser", user.getImg());
            
            // imposto una sessione con il nome dell'immagine per averne accesso in tutte le pagine
            session.setAttribute("imgUser", user.getImg());
            session.setAttribute("userName", user.getNome());
            // System.out.println("utentePhoto ---- " + user.getImg());

            return "/index";
        }
        return "redirect:/formlogin"; 
    }

    @GetMapping("/formlogin")
    public String formlogin() {
        return "/formlogin";
    }
    
    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam("email") String email, @RequestParam("password") String passw, Model model) {

        //uso il metodo che ho creato in UserRepository.java
        User findUser = userRepository.findUserByEmail(email);

        // System.err.println("cerca user -> " + findUser);
     

        if(findUser != null && passwordEncoder.pwMaches(passw, findUser.getPassword())) {
            session.setAttribute("user", findUser.getId());
            
            //imposto il ruolo dell'utente nella sessione
            session.setAttribute("ruolo", findUser.getRuolo().toUpperCase());
            return "redirect:/";
        }

        //nel caso di acceso fallito mandiamo il messaggio col model
        model.addAttribute("error", "Credenziali non valide");

        return "/formlogin";
    }
    



    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}
