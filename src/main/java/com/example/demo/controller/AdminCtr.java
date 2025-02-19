package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminCtr {
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/users")
	public String listUser(Model model,HttpSession session) {
		//verifico se l'utente e' admin
		if(!"ADMIN".equals(session.getAttribute("ruolo"))) {
			return "redirect:/";
		};
		model.addAttribute("userList", userRepo.findAll());
		return "userList";
	}
	@GetMapping("changeRole/{userId}")
	public String changeRoleForm(@PathVariable("userId") int userId, Model model, HttpSession session) {
		//controlliamo se un utente e' admin
		String userRole = (String) session.getAttribute("ruolo");
		if(userRole == null && userRole.equalsIgnoreCase("ADMIN")) {
			return "redirect:/";
		}
		
		//carica utente da modificare
		User user = userRepo.findById(userId).orElse(null);
		if(user == null) {
			return "redirect:/";
		}
		
		//passo utente al modello
		model.addAttribute("user",user);
		return "changeRoleForm";
	}
	@PostMapping("changeRole")
	public String changeRole(@RequestParam("userId") int userId, @RequestParam("ruolo") String ruolo, Model model, HttpSession session) {
		System.out.println("📩 Richiesta ricevuta!");
	    System.out.println("userId: " + userId);
	    System.out.println("ruolo: " + ruolo);
		
		//controllo se utente e' un admin
		String userRole = (String) session.getAttribute("ruolo");
		if(userRole ==null || !userRole.equalsIgnoreCase("ADMIN")) {
			return "redirect:/";
		}
		
		//recupero utente e aggiorno il suo ruolo
		User user = userRepo.findById(userId).orElse(null);
		if(user != null) {
			user.setRuolo(ruolo.toUpperCase());
			userRepo.save(user);
		}
		return "redirect:/";
	}
	
	
}
