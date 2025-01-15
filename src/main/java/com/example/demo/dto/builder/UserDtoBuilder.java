package com.example.demo.dto.builder;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

public class UserDtoBuilder {
	public static User UserFromDtoToEntity (UserDto uDto) {
		User u = new User();
		u.setId(uDto.getId());
		u.setNome(uDto.getNome());
		u.setEmail(uDto.getEmail());
		u.setPassword(uDto.getPassword());
		u.setDataNascita(uDto.getDataNascita());
		u.setSegnoZodiacale(uDto.getSegnoZodiacale());
		return u;
	}
	
	public static UserDto UserFromEntityToDto (User u) {
		UserDto uDto = new UserDto();
		uDto.setId(u.getId());
		uDto.setNome(u.getNome());
		uDto.setEmail(u.getEmail());
		uDto.setPassword(u.getPassword());
		uDto.setDataNascita(u.getDataNascita());
		uDto.setSegnoZodiacale(u.getSegnoZodiacale());
		return uDto;
	}
}
