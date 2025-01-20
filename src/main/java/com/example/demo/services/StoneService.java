package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	private String saveImage(MultipartFile imageFile) {
		 // Nome file univoco (per evitare conflitti)
	    String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
	    
	    // Percorso della cartella uploads sotto la cartella static
	    String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

	    // Crea la cartella se non esiste
	    File dir = new File(uploadDir);
	    if (!dir.exists()) {
	        boolean created = dir.mkdirs();  // Crea la cartella
	        if (created) {
	            System.out.println("La cartella 'uploads' è stata creata correttamente.");
	        } else {
	            System.out.println("La cartella 'uploads' esiste già.");
	        }
	    }

	    // Percorso completo del file da salvare
	    File dest = new File(uploadDir + fileName);

	    try {
	        // Salva il file nel server
	        imageFile.transferTo(dest);
	        System.out.println("File salvato correttamente: " + dest.getAbsolutePath());
	        return fileName;
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Errore durante il salvataggio dell'immagine", e);
	    }
	}
	
	public void inserisciPietra(StoneDto sDto) {
		Stone s = StoneDtoBuilder.StoneFromDtoToEntity(sDto);
		
		 // Gestisci l'immagine se è presente
	    if (sDto.getImmagineFile() != null && !sDto.getImmagineFile().isEmpty()) {
	        String imageName = saveImage(sDto.getImmagineFile());
	        s.setImmagine(imageName);  // Memorizza solo il nome del file
	    }
		stoneRepo.save(s);
	}
	
	public StoneDto aggiornaPietra(int id) {
		
		return StoneDtoBuilder.StoneFromEntityToDto(stoneRepo.findById(id).orElse(new Stone()));
	}
}
