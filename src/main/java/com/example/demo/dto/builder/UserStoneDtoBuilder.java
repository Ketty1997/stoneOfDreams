package com.example.demo.dto.builder;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.StoneDto;
import com.example.demo.dto.UserStoneDto;
import com.example.demo.model.Stone;
import com.example.demo.model.User;
import com.example.demo.model.UserStone;

public class UserStoneDtoBuilder {
	public static UserStone userStoneFromDtoToEntity(UserStoneDto uDto) {
		UserStone u = new UserStone();
		u.setId(uDto.getId());
		u.setUser(new User(uDto.getUserId()));
		u.setStone(new Stone(uDto.getStoneId()));
		u.setData(uDto.getData());
		u.setNote(uDto.getNote());
		u.setImagePath(uDto.getImagePath());
		return u;
	}

	public static List<UserStoneDto> userStonefromEntityToDto(List<UserStone> userStones){
		List<UserStoneDto> userStoneDtos = new ArrayList<>();
		for (UserStone pietra : userStones) {
			userStoneDtos.add(userStoneFromEntityToDto(pietra));
		}
		return userStoneDtos;
	}
	
	public static UserStoneDto userStoneFromEntityToDto(UserStone u) {
		UserStoneDto uDto = new UserStoneDto();
		uDto.setId(u.getId());
		uDto.setUserId(u.getUser().getId());
		uDto.setStoneId(u.getStone().getId());
		uDto.setData(u.getData());
		uDto.setNote(u.getNote());
		uDto.setImagePath(u.getImagePath());
		return uDto;
	}

}
