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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.services.UserService;
import com.example.demo.util.PasswordEncoder;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("user")
public class UserCtr {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping({"","/"})
	public String userDetails(HttpSession session, Model model) {
	
		//recupero l'utente dalla sessione
		User user = userService.getUserFromSession(session);

		System.out.println("Ultima lettera nome utente: " + user.getNome().charAt(user.getNome().length() -1));
		System.out.println("immagine utente: " + user.getImg());
		String gender="";
		switch (user.getNome().charAt(user.getNome().length() -1)) {
			case 'a':
				gender="donna";
			break;
			case 'o':
				gender="uomo";
			break;
		}

		//passa le informazioni al modello
		model.addAttribute("user", user);
		if (user.getImg() == null && gender.length() >=1) {
			
			model.addAttribute("gender", gender);
		}
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
	public String update(Model model, @ModelAttribute("updateU") @Valid UserDto uDto, BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "updateUser";
	    }
		//recupero utente dal db per prendere l'immagine attuale
	    User currentUser = userService.getUserById(uDto.getId());
	    //se l'utente esiste prendi il valore dell'immagine altrimenti porta storageFileName a null
	    String storageFileName = (currentUser != null) ? currentUser.getImg() : null;


	    // Gestione dell'immagine
	    if (uDto.getImg() != null && !uDto.getImg().isEmpty()) {
	        // Se è stata fornita una nuova immagine, la salviamo
	        try {
	            storageFileName = userService.saveImage(uDto.getImg());
	        } catch (IOException e) {
	            result.addError(new FieldError("updateU", "img", "Errore durante il caricamento dell'immagine: " + e.getMessage()));
	            return "updateUser";
	        }

	    }


		//dobbiamo verificare se la pass inserita è uguale a quella dell'utente nel db quindi cerchiamo l'utente e usiamo la passw per vedere se è uguale
		User user = userService.getUserById(uDto.getId());
		
		String passwConf = uDto.getConfermaPass();
		System.out.println("la password scritta è "+passwConf);
		System.out.println("la password scritta è uguale a quella hashata? "+ passwordEncoder.pwMaches(passwConf, user.getPassword()));

	    // Gestione della data di nascita
	    // Converti la data di nascita da stringa a LocalDate
	    if (uDto.getFormattedDataNascita() != null && !uDto.getFormattedDataNascita().isEmpty()) {
	        uDto.setDataNascita(LocalDate.parse(uDto.getFormattedDataNascita()));
		}

	    try {
			
			if(passwordEncoder.pwMaches(passwConf, user.getPassword())) {

				uDto.setPassword(uDto.getConfermaPass());
				// Salva l'utente con l'immagine (se nuova), password (se nuova) o data di nascita (se nuova)
				userService.saveUser(uDto, storageFileName);
				System.out.println("Utente salvato con successo.");
			}else {
				//se non è uguale genera l'errore aggiungere un'eccezione manuale o un messaggio di errore con FieldError
				result.addError(new FieldError("updateU", "confermaPass", "La password inserita non è corretta."));
				return "updateUser"; // Torna alla pagina HTML con l'errore
			}
	    } catch (Exception e) {
			//rimandiamo un errore che viene stampato con un <p th:if="${#fields.hasErrors('confermaPass')}" th:errorclass="text-danger" th:errors="*{confermaPass}"></p>
	        result.addError(new FieldError("updateU", "nome", "Errore durante il salvataggio: " + e.getMessage()));
	        return "updateUser";
	    }

		redirectAttributes.addFlashAttribute("successMessage", "L'utente è stato correttamente aggiornato.");
	    return "redirect:/user";  // Redirigi alla pagina di successo
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
		
		//verifica che la vecchia password sia corretta
		//richiamo il metodo nel service, se il metodo restituisce false a
		if(!userService.verificaVecchiaPassword(currentUser, password.getOldPassword())) {
			result.addError(new FieldError("changePasswordForm", "oldPassword", "Vecchia password errata!"));
	        return "changePasswordForm";
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
	public String elimina(@PathVariable int id, HttpSession session) {
		userService.eliminaUser(id);
		session.invalidate();
		return"success";
	}

}
