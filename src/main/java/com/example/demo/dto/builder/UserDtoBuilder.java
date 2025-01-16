package com.example.demo.dto.builder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

public class UserDtoBuilder {

	public static User UserFromDtoToEntity (UserDto uDto) {

		MultipartFile userImage = uDto.getImg();
		String userImageName = userImage.getOriginalFilename();

		User u = new User();
		// u.setId(uDto.getId());
		u.setNome(uDto.getNome());
		u.setEmail(uDto.getEmail());
		u.setPassword(uDto.getPassword());
		u.setDataNascita(uDto.getDataNascita());
		u.setSegnoZodiacale(uDto.getSegnoZodiacale());
		u.setImg(userImageName);
		return u;
	}
	
	public static UserDto UserFromEntityToDto (User u) {
		

		UserDto uDto = new UserDto();
		// uDto.setId(u.getId());
		uDto.setNome(u.getNome());
		uDto.setEmail(u.getEmail());
		uDto.setPassword(u.getPassword());
		uDto.setDataNascita(u.getDataNascita());
		uDto.setSegnoZodiacale(u.getSegnoZodiacale());
		//capire come trasformare da stringa immagine a file multipart (e se ha senso)
		// uDto.setImg(u.getImg());
		return uDto;
	}

}
