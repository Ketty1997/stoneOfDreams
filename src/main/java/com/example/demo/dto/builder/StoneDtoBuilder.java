package com.example.demo.dto.builder;

import com.example.demo.dto.StoneDto;
import com.example.demo.model.Stone;

public class StoneDtoBuilder {
	public static Stone StoneFromDtoToEntity(StoneDto sDto) {
		Stone s = new Stone();
		s.setId(sDto.getId());
		s.setNome(sDto.getNome());
		s.setTipo(sDto.getTipo());
		s.setColore(sDto.getColore());
		s.setElemento(sDto.getElemento());
		s.setEmozione(sDto.getEmozione());
		s.setDescrizione(sDto.getDescrizione());
		s.setImmagine(sDto.getImmagine());
		return s;
	}
	
	public static StoneDto StoneFromEntityToDto(Stone s) {
		StoneDto sDto = new StoneDto();
		sDto.setId(s.getId());
		sDto.setNome(s.getNome());
		sDto.setTipo(s.getTipo());
		sDto.setColore(s.getColore());
		sDto.setElemento(s.getElemento());
		sDto.setEmozione(s.getEmozione());
		sDto.setDescrizione(s.getDescrizione());
		sDto.setImmagine(s.getImmagine());
		return sDto;
	}
}
