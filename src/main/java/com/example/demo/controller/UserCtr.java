package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.services.UserService;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("user")
public class UserCtr {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String userDetails(HttpSession session, Model model) {
		//controllo se l'utente e' loggato altrimenti lo mando alla pagina login
		if(session.getAttribute("user")==null) {
			return "redirect:/formlogin";
		}
		//recupero l'utente dalla sessione
		User user = userService.getUserFromSession(session);
		if(user==null) {
			return "redirect:/formlogin";
		}
		//passa le informazioni al modello
		model.addAttribute("user", user);
		return "userDetails";
	}
	
	@GetMapping("preUpdateUser/{id}")
	public String preUpdate(Model model, @PathVariable int id) {
		  UserDto userDto = userService.aggiornaUser(id);
		   // Se dataNascita è non null, la formattiamo come stringa
		    if (userDto.getDataNascita() != null) {
		        userDto.setFormattedDataNascita(userDto.getDataNascita().toString()); // Conversione in formato 'yyyy-MM-dd'
		    }
		model.addAttribute("updateU", userDto);
		return "updateUser";
	}
	
	@PostMapping("updateUser")
	public String update(Model model, @ModelAttribute("updateU") @Valid UserDto uDto, BindingResult result) {
		if (result.hasErrors()) {
	        // Mostra gli errori di validazione
	        System.out.println("Errori di validazione: " + result.getAllErrors());
	        return "updateUser";
	    }

	    String storageFileName = null;

	    // Gestione dell'immagine
	    if (uDto.getImg() != null && !uDto.getImg().isEmpty()) {
	        // Se è stata fornita una nuova immagine, la salviamo
	        try {
	            storageFileName = userService.saveImage(uDto.getImg());
	        } catch (IOException e) {
	            result.addError(new FieldError("updateU", "img", "Errore durante il caricamento dell'immagine: " + e.getMessage()));
	            return "updateUser";
	        }
	    } else {
	        // Se non è stata fornita una nuova immagine, manteniamo quella esistente
	        storageFileName = null; // O in alternativa, puoi passare il nome del file esistente se è stato caricato prima
	    }

	    // Escludiamo la password dalle modifiche
	    uDto.setPassword(null);
	    
	    // Gestione della data di nascita
	    
	    // Converti la data di nascita da stringa a LocalDate
	    if (uDto.getFormattedDataNascita() != null && !uDto.getFormattedDataNascita().isEmpty()) {
	        uDto.setDataNascita(LocalDate.parse(uDto.getFormattedDataNascita()));
	        }

	    try {
	        // Salva l'utente con l'immagine (se nuova), password (se nuova) o data di nascita (se nuova)
	        userService.saveUser(uDto, storageFileName);
	        System.out.println("Utente salvato con successo.");
	    } catch (Exception e) {
	        result.addError(new FieldError("updateU", "nome", "Errore durante il salvataggio: " + e.getMessage()));
	        return "updateUser";
	    }

	    return "success";  // Redirigi alla pagina di successo
	}
	
	@GetMapping("preChangePassword")
	public String preChangePassword(Model model, HttpSession session) {
		if(session.getAttribute("user")==null) {
			return "redirect:/formLogin";
		}
		model.addAttribute("changePasswordForm", new UserDto());
		return "changePasswordForm";
	}
	@PostMapping("changePassword")
	public String changePwd(HttpSession session, @ModelAttribute("changePasswordForm") UserDto password, BindingResult result) {
	
		   if(result.hasErrors()) {
			return "changePasswordForm";
		}
		System.out.println("Password ricevuta: " + password.getPassword());
		User currentUser = userService.getUserFromSession(session);
		if(currentUser==null) {
			return "redirect:/formLogin";
		}
		try {
			userService.updatePassword(currentUser.getId(), password.getPassword());
			
		}catch(Exception e) {
			result.addError(new FieldError("changePasswordFrom", "error", "errore durante l'operazione"));
			return "changePasswordForm";
		}
		return "success";
	}
	@GetMapping("delete/{id}")
	public String elimina(@PathVariable int id) {
		userService.eliminaUser(id);
		return"success";
	}

}
