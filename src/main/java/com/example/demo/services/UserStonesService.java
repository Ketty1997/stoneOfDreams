package com.example.demo.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addStoneToCollection(int userId, int stoneId, String nota, LocalDate data ) {
        UserStoneDto stone = new UserStoneDto();
        stone.setNote(nota);
        stone.setStoneId(stoneId);
        stone.setUserId(userId);
        stone.setData(data);

        UserStone userStone = UserStoneDtoBuilder.userStoneFromDtoToEntity(stone);

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
}
