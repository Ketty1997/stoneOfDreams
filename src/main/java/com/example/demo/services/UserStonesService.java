package com.example.demo.services;

import java.time.LocalDate;
import java.util.List;

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


    public List<Stone> listaPietreUtente(Integer idUser) {
        // UserStoneDtoBuilder.userStonefromEntityToDto(userStoneRepository.findAll());
        List<Stone> userStones  = userStoneRepository.findStonesByUserId(idUser);
        return userStones;
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
    
}
