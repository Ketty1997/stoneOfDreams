package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.HoroscopeService;
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
    
    @Autowired
    private HoroscopeService horoscopeService;
    

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
            //Se l'utente ha un segno zodiacale registrato, chiamo l'api per ottenere l'oroscopo
            if(user.getSegnoZodiacale() != null && !user.getSegnoZodiacale().isEmpty()) {

                try {
                   
                    String messaggioOroscopo = horoscopeService.getHoroscope(user.getSegnoZodiacale().toLowerCase());
                
                    String primaParteTradotta = "";
                    String secondaParteTradotta = "";
                
                    //substring(0, 500) lancia StringIndexOutOfBoundsException se la stringa è più corta di 500 caratteri.
                    // Per evitare l'eccezione, dobbiamo usare if (messaggioOroscopo.length() > 500)
                    if (messaggioOroscopo.length() > 500) {
                        primaParteTradotta = messaggioOroscopo.substring(0, 500);
                        secondaParteTradotta = messaggioOroscopo.substring(500);
                    } else {
                        primaParteTradotta = messaggioOroscopo;
                    }
                
                    String messTradotto = horoscopeService.traduciFrase(primaParteTradotta);
                    if (!secondaParteTradotta.isEmpty()) {
                        messTradotto += horoscopeService.traduciFrase(secondaParteTradotta);
                    }
                
                    model.addAttribute("messaggioOroscopo", messaggioOroscopo);
                    model.addAttribute("messaggioOroscopoTradotto", messTradotto);
                } catch (Exception e) {
                    System.out.println("errore: -> " + e.getMessage());
                }
                    
            }

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
