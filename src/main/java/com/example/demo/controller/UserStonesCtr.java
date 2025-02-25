package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.dto.UserStoneDto;
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

    
    @PostMapping("/addToColl") 
    public String handlePostAddToCollection(@RequestParam int stoneid, @RequestParam String nota,@RequestParam("image")MultipartFile image, UserStoneDto uDto, HttpSession session) throws IOException {
        // Simile alla logica di GET, ma ora gestito per la richiesta POST
        User utente = userService.getUserFromSession(session);
        int idUtente = utente.getId();
        LocalDate data = LocalDate.now();
    
        String storageFileName = userStonesService.saveImage(image);
        // Aggiungi pietra alla collezione
        userStonesService.addStoneToCollection(idUtente, stoneid, nota, data,storageFileName);

        return "redirect:/userStones";
    }
    
   
    
    @GetMapping("/editNote")
    public String editNote(@RequestParam int id, Model model) {
    	//recupero la nota e la pietra
    	
    	UserStone userStone =userStonesService.getUserStoneById(id);
    	model.addAttribute("pietra", userStone);
    	return "userStonesEditNote";
    }


    @PostMapping("/updateNote")
    public String updateNote(@RequestParam("id") int id, @RequestParam("nota") String nota){
    	 System.out.println("Nota ricevuta per l'ID " + id + ": " + nota);
    	//recupero pietra
    	UserStone userStone = userStonesService.getUserStoneById(id);
    	//aggiorno nota
    	userStone.setNote(nota);
    	//salvo modifice
    	userStonesService.updateUserStone(userStone);
    	
    	return "redirect:/userStones";
    	
    }

}
