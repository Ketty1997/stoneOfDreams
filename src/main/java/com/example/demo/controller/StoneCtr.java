package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

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


	@PostMapping("updateStone")
	public String update(Model model, @ModelAttribute("updateS") @Valid StoneDto sDto, BindingResult result, RedirectAttributes redirectAttributes) {
		 // 1. Controlla se ci sono errori di validazione nel form
	    //    Il parametro `BindingResult` contiene eventuali errori di validazione del DTO (StoneDto).
	    //    Se ci sono errori, torna indietro alla pagina di aggiornamento mostrando gli errori.
		
		if (result.hasErrors()) {
	        return "updateStone";
	    }

	    // 2. Inizializza una variabile per il nome del file immagine.
	    //    Questo sarà il nome dell'immagine salvata nella directory.
	    String storageFileName = null;

	    // 3. Verifica se il campo immagine nel form non è vuoto (l'utente ha caricato un file).
	    if (!sDto.getImmagineFile().isEmpty()) {
	    	// 4. Chiama il metodo `saveImage` nel service per salvare il file nella directory
            //    e assegna il nome del file salvato a `storageFileName`.
	        try {
	            storageFileName = stoneService.saveImage(sDto.getImmagineFile());
	        } catch (org.springframework.web.multipart.MaxUploadSizeExceededException e) {
		    	// 5. Gestisce l'errore specifico in cui l'immagine supera la dimensione massima consentita.
		        //    Aggiunge un messaggio di errore specifico e torna al form.

		        result.addError(new FieldError("updateS", "immagineFile", "L'immagine che hai selezionato è troppo grande! Il limite massimo è 10MB."));
		        return "updateStone";
		    }
	        catch (IOException e) {
	        	 // 6. Gestisce errori generici legati al salvataggio del file.
	            //    Aggiunge un errore a `BindingResult` per mostrarlo nel form
	            result.addError(new FieldError("updateS", "immagineFile", "Errore durante il caricamento dell'immagine: " + e.getMessage()));
				return "redirect:/stone";

	        }
	    }

	    //7. Dopo aver salvato il file immagine (se presente), prova a salvare la pietra nel database.
	    try {
	    	// Usa il servizio per salvare i dati nel database, passando il DTO e il nome dell'immagine.
	        stoneService.inserisciPietra(sDto, storageFileName);
			redirectAttributes.addFlashAttribute("successMessage", "La pietra è stata correttamente aggiornata.");
	    }
	    catch (Exception e) {
	    	// 8. Gestisce eventuali altri errori generici durante il salvataggio della pietra nel database.
	       result.addError(new FieldError("updateS", "nome", "Errore durante il salvataggio: " + e.getMessage()));
	        return "updateStone";
	    }

	    return "redirect:/stone";
	}

	@GetMapping("/delete/{id}")
	public String elimina(@PathVariable int id) {
		stoneService.eliminaPietra(id);
		return "redirect:/stone";
	}

}
