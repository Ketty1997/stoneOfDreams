package com.example.demo.dto.builder;

import com.example.demo.dto.UserStoneDto;
import com.example.demo.model.Stone;
import com.example.demo.model.User;
import com.example.demo.model.UserStone;

public class UserStoneDtoBuilder {
	public static UserStone UserStoneFromDtoToEntity(UserStoneDto uDto) {
		UserStone u = new UserStone();
		u.setId(uDto.getId());
		u.setUser(new User(uDto.getUserId()));
		u.setStone(new Stone(uDto.getStoneId()));
		u.setData(uDto.getData());
		u.setNote(uDto.getNote());
		return u;
	}
	
	public static UserStoneDto UserStoneFromEntityToDto(UserStone u) {
		UserStoneDto uDto = new UserStoneDto();
		uDto.setId(u.getId());
		uDto.setUserId(u.getUser().getId());
		uDto.setStoneId(u.getStone().getId());
		uDto.setData(u.getData());
		uDto.setNote(u.getNote());
		return uDto;
	}

}
