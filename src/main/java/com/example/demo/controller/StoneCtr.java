package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.StoneDto;
import com.example.demo.services.StoneService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("stone")
public class StoneCtr {
	@Autowired
	private StoneService stoneService;
	
	
	
	@GetMapping({"","/"})
	public String listaPietre(Model model) {
		List<StoneDto> listaPietre = stoneService.getListaPietre();
		model.addAttribute("listaPietre", listaPietre);
		return "stones";
	}
	
	@GetMapping("preInsertStone")
	public String preInsert(Model model) {
		StoneDto sDto = new StoneDto();
		model.addAttribute("stoneForm",sDto);
		return "insertStone";
	}
	@PostMapping("inserisciPietra")
	public String insert(Model model, @Valid @ModelAttribute("stoneForm") StoneDto sDto, BindingResult result) {
		/*abbiamo tra i paramentri l'oggetto dto passato dalla form, e l'annotation @Valid serve a validare
        * e per vedere se ci sono errori di validazione dobbiamo aggiungere tra il parametro BindingResult che controlla se
        * ci sono errori con i dati di stoneDto -> vedi nel dto il messaggio  @NotEmpty(message = "The username is required") ---

        * --- In stoneDto per il campo imageFile non abbiamo una validazione gia impostata come con gli altri parametri, ma
        * è importante che sia presente, quindi possiamo scriverla nel StoneDto.java a mano:*/
        // if(stoneDto.getImageFile().isEmpty()){ -> per farlo direttamnete qua ma meglio nel service
        //     result.addError(new FieldError("productDto","imageFile", "the image file is required"));
        // }

        //uso questo con service al posto di questo appena sopra perché ho impostato li un metodo per il controllo invece di farlo qui
        stoneService.validateImageFile(sDto, result);

        //controlliamo se è presente qualche errore di validazione:
        if(result.hasErrors()) {
			//in caso di errori ecc rimanderà di nuovo alla stessa pagina con gli errori -> gli errori li inseriamo nell'html tramite thymeleaf come un if
			//es. <p th:if="${#fields.hasErrors('nome')}" th:errorclass="text-danger" th:errors="${stoneForm.nome}">
            return "/insertStone";
        }

        //se non abbiamo errori salviamo il file immagine nella cartella tramite il metodo creato nel service
        try {
			//creo una variabile che contiene il file del nome dell'immagine e lo uso anche per salvarla nella cartella con il metodo nel service 
            String storageFileName = stoneService.saveImage(sDto.getImmagineFile());

            //salviamo l'elemento nel db, gli passiamo anche il file del nome come stringa per usarlo poi nel builder dal service 
            stoneService.inserisciPietra(sDto, storageFileName);


        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            // return "/signup";
        }

		return "redirect:/stone";
		
	}
	
	@GetMapping("preUpdateStone/{id}")
	public String preUpdate(Model model, @PathVariable int id) {
		model.addAttribute("updateS", stoneService.aggiornaPietra(id));
		return "updateStone";
	}
	// @PostMapping("updateStone")
	// public String update(Model model, @ModelAttribute("updateS") StoneDto sDto) {
	// 	stoneService.inserisciPietra(sDto);
	// 	return "success";
	// }
	@GetMapping("/delete/{id}")
	public String elimina(@PathVariable int id) {
		stoneService.eliminaPietra(id);
		return"success";
	}

}
