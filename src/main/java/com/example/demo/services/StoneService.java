package com.example.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.dto.StoneDto;
import com.example.demo.dto.builder.StoneDtoBuilder;
import com.example.demo.model.Stone;
import com.example.demo.repository.StoneRepository;

@Service
public class StoneService {
	

	@Autowired
	private StoneRepository stoneRepo;
	
	public List<StoneDto> getListaPietre(){
		return StoneDtoBuilder.StonefromEntityToDto(stoneRepo.findAll());
	}
	
	public void eliminaPietra(int id) {
		stoneRepo.deleteById(id);
	}


	
    public String saveImage(MultipartFile image) throws IOException {
        
        /* Questo blocco di codice mostra un'implementazione del concetto di try-with-resources, una funzionalità introdotta in Java 7 che semplifica 
        la gestione delle risorse che devono essere chiuse, come file, stream o connessioni di rete. Esaminiamo ogni parte del codice:
        InputStream inputStream = image.getInputStream(): Questo esprime che stai aprendo una risorsa, in questo caso un InputStream ottenuto dall'oggetto image. 
        Il metodo image.getInputStream() restituisce un flusso di input che può essere utilizzato per leggere i dati contenuti in image.
        
        -inputStream: È il flusso di input da cui leggere i dati. In questo caso, i dati vengono letti dall'immagine rappresentata dall'oggetto image.
        -Paths.get(uploadDir + storageFileName): Questo costruisce il percorso completo dove il file verrà salvato. uploadDir è la directory in cui il file 
        verrà memorizzato, e storageFileName è il nome del file.
        -StandardCopyOption.REPLACE_EXISTING: Questa opzione specifica che, se un file esiste già nella destinazione con lo stesso nome, esso verrà sovrascritto.*/
        
        String stoneImageName = image.getOriginalFilename();

        try {
            String uploadDir = "src/main/resources/static/images/";
            Path uploadPath = Paths.get(uploadDir);

            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + stoneImageName), StandardCopyOption.REPLACE_EXISTING);
                // System.out.println("sono ne try caricamento nella cartella");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        } catch (Exception e) {
            System.out.println("errore-> " + e.getMessage());
        }

        return stoneImageName;
    }


	public void inserisciPietra(StoneDto sDto, String storageFileName) {

		Stone stone = StoneDtoBuilder.StoneFromDtoToEntity(sDto, storageFileName);
		
		stoneRepo.save(stone);
	}
	
	public StoneDto aggiornaPietra(int id) {
		
		return StoneDtoBuilder.StoneFromEntityToDto(stoneRepo.findById(id).orElse(new Stone()));
	}



    public void validateImageFile(StoneDto stoneDto, BindingResult result) {
        /* --- In StoneDto per il campo immagineFile non abbiamo una validazione gia impostata come con gli altri parametri, ma
        * è importante che sia presente, quindi possiamo anche scriverla qui a mano:*/
        if (stoneDto.getImmagineFile().isEmpty()) {
			// in caso di immagine vuota, irmandiamo questo messaggio di errore tramite result al ctr che poi lo rimanda come errore e lo stampa nell'html nella parte th:if dell'immagine
            result.addError(new FieldError("stoneForm", "immagineFile", "the image file is required"));
        }
    }


}
