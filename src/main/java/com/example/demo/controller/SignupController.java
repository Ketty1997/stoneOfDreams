package com.example.demo.controller;

import java.io.InputStream;
import java.net.PasswordAuthentication;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;
import com.example.demo.util.PasswordEncoder;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signup(Model model) {

        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);

        return "/signup.html";
    }




    @PostMapping("/signupProcess")
    public String signupProcess(@Valid @ModelAttribute UserDto userDto, BindingResult result, HttpSession session) {
        
        
        //uso questo con service al posto di questo appena sopra perché ho impostato li un metodo per il controllo invece di farlo qui
        userService.validateImageFile(userDto, result);

        //controlliamo se è presente qualche errore di validazione:
        if(result.hasErrors()) {
            return "/singup";
        }

        //se non abbiamo errori salviamo il file immagine nella cartella tramite il meodo creato nel service
        try {
            String storageFileName = userService.saveImage(userDto.getImg());
            
            //prendo l'utente tramite la session
            // User user = userService.getUserFromSession(session);
            User user = userService.getUserFromSession(session);
            // User user = userRepo.findById((Integer) session.getAttribute("user")).get();

            //salviamo l'elemento nel db
            userService.saveUser(userDto, storageFileName, user);

            return "redirect:formlogin";


        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            // return "/signup";
        }



        return "formlogin";
        
    }
    
    
}
