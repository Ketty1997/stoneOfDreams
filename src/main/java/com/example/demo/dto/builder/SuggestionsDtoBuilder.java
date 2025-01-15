package com.example.demo.dto.builder;

import com.example.demo.dto.SuggestionsDto;
import com.example.demo.model.Stone;
import com.example.demo.model.Suggestions;
import com.example.demo.model.User;

public class SuggestionsDtoBuilder {
	public static Suggestions SuggestionsFromDtoToEntity(SuggestionsDto sDto) {
		Suggestions s = new Suggestions();
		s.setId(sDto.getId());
		s.setUser(new User(sDto.getUserId()));
		s.setStone(new Stone(sDto.getStoneId()));
		s.setDataSuggerimento(sDto.getDataSuggerimento());
		s.setMotivo(sDto.getMotivo());
		return s;
	}
	
	public static SuggestionsDto SuggestionsFromEntityToDto(Suggestions s) {
		SuggestionsDto sDto = new SuggestionsDto();
		sDto.setId(s.getId());
		sDto.setUserId(s.getUser().getId());
		sDto.setStoneId(s.getStone().getId());
		sDto.setDataSuggerimento(s.getDataSuggerimento());
		sDto.setMotivo(s.getMotivo());
		return sDto;
	}
}
