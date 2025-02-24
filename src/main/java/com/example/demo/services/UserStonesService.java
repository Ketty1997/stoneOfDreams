package com.example.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.UserStoneDto;
import com.example.demo.dto.builder.UserStoneDtoBuilder;
import com.example.demo.model.Stone;
import com.example.demo.model.UserStone;
import com.example.demo.repository.UserStoneRepository;

@Service
public class UserStonesService {

    @Autowired
    private UserStoneRepository userStoneRepository;


    public Map<Stone, UserStone> listaPietreUtente(Integer idUser) {

        Map<Stone, UserStone> stonesUserMap = new HashMap<>();

        List<Stone> userStones  = userStoneRepository.findStonesByUserId(idUser);
        int sizeMap = userStones.size();
       
        List<UserStone> UserStoneTab = userStoneRepository.findAll();


        for (int i = 0; i < sizeMap; i++) {
            // System.out.println(userStones.get(i).getNome());
            // System.out.println(UserStoneTab.get(i).getNote());
            // System.out.println(UserStoneTab.get(i).getId());

            stonesUserMap.put(userStones.get(i), UserStoneTab.get(i));
        }

        // prova di stampa elementi
        // stonesUserMap.forEach((stone, description) -> 
        //     System.out.println("sotone: " + stone.getNome() + "descrizione: " +  description)
        // );

        return stonesUserMap;

    } 

    public void addStoneToCollection(int userId, int stoneId, String nota, LocalDate data, String storageFileName) {
        UserStoneDto stone = new UserStoneDto();
        stone.setNote(nota);
        stone.setStoneId(stoneId);
        stone.setUserId(userId);
        stone.setData(data);
        System.out.println("Storage file name: " + storageFileName);
        stone.setImagePath(storageFileName);
        UserStone userStone = UserStoneDtoBuilder.userStoneFromDtoToEntity(stone);
        // Stampa i dettagli della pietra salvata
        System.out.println("Pietra salvata: " + userStone.toString());
        userStoneRepository.save(userStone);

    }

    public void deleteUserStone(int id) {

        
        try {
            
            userStoneRepository.deleteByStoneId(id);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    public UserStone getUserStoneById(int id) {
        Optional<UserStone> userStone = userStoneRepository.findById(id);
        if (userStone.isPresent()) {
            return userStone.get();
        } else {
            throw new RuntimeException("Pietra con ID " + id + " non trovata");
        }
    }

    public void updateUserStone(UserStone us) {
    	userStoneRepository.save(us);
    }
    
    public boolean existingInCollection(int userId, int stoneId) {
    	return userStoneRepository.existsByUserIdAndStoneId(userId, stoneId);
    }
    public List<Integer> getUserStoneIds(int userId) {
        return userStoneRepository.findStoneIdsByUserId(userId);
    }
    
        
    	public String saveImage(MultipartFile image) throws IOException {
    		//controllo file se e' nullo o vuoto viene sollevata un'eccezione
    	    if (image == null || image.isEmpty()) {
    	        throw new IllegalArgumentException("Il file immagine è vuoto o nullo");
    	    }

    	    // Normalizza il nome del file per evitare caratteri speciali, prendo il nome originale dell'imagine e rimpiazzo tutti quei caratteri strani
    	    String normalizedFileName = image.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

    	    // crea un oggetto PATH che rappresenta il percorso della directory dove vogliamo salvare il file
    	    Path uploadPath = Paths.get("src/main/resources/static/images");

    	    //Aggiunge il nome del file normalizzato al percorso di caricamento definito prima(uploadPath())
    	    //esempio se normalizedFileName è "immagine_123.jpg" e uploadPath è "src/main/resources/static/images", allora filePath sarà "src/main/resources/static/images/immagine_123.jpg".
    	    Path filePath = uploadPath.resolve(normalizedFileName);
    	    //questo e' il passo in cui il file viene effetivamente salvato sul disco, aprendo un flusso di input INPUTSTREAM che permette di leggere il contenuto del file caricato
    	    try (InputStream inputStream = image.getInputStream()) {
    	        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    	        //eccezione se si verifica un errore
    	    } catch (IOException e) {
    	        System.err.println("Errore durante il salvataggio del file: " + e.getMessage());
    	        throw new IOException("Errore durante il salvataggio del file immagine", e);
    	    }

    	    // Ritorna il nome del file salvato
    	    return normalizedFileName;
    	

    }
}
