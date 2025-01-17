package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.StoneDto;
import com.example.demo.dto.builder.StoneDtoBuilder;
import com.example.demo.repository.StoneRepository;

@Service
public class StoneService {
	@Autowired
	private StoneRepository stoneRepo;
	
	public List<StoneDto> getListaPietre(){
		return StoneDtoBuilder.StonefromEntityToDto(stoneRepo.findAll());
	}
}
