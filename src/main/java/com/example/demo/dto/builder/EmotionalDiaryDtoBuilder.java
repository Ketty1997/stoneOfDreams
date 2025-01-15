package com.example.demo.dto.builder;

import com.example.demo.dto.EmotionalDiaryDto;
import com.example.demo.model.EmotionalDiary;
import com.example.demo.model.User;

public class EmotionalDiaryDtoBuilder {
	public static EmotionalDiary EmotionalDiaryFromDtoToEntity(EmotionalDiaryDto eDto) {
		EmotionalDiary e = new EmotionalDiary();
		e.setId(eDto.getId());
		e.setUser(new User(eDto.getIdUser()));
		e.setData(eDto.getData());
		e.setStatoAnimo(eDto.getStatoAnimo());
		return e;
	}
	
	public static EmotionalDiaryDto EmotionalDiaryFromEntityToDto(EmotionalDiary e) {
		EmotionalDiaryDto eDto = new EmotionalDiaryDto();
		eDto.setId(e.getId());
		eDto.setIdUser(e.getUser().getId());
		eDto.setData(e.getData());
		eDto.setStatoAnimo(e.getStatoAnimo());
		return eDto;
	}
}
