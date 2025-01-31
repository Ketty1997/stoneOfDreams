package com.example.demo.dto.builder;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

@Component // -> importante che sia annotata con Component per essere gestita da spring
public class UserDtoBuilder {


	public static User UserFromDtoToEntity (UserDto uDto, String imageFileName, String passw) {


		User u = new User();
		u.setId(uDto.getId());
		u.setNome(uDto.getNome());
		u.setEmail(uDto.getEmail());
		u.setPassword(passw);
		u.setDataNascita(uDto.getDataNascita());
		u.setSegnoZodiacale(uDto.getSegnoZodiacale());
		u.setImg(imageFileName);
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
		//capire come trasformare da stringa immagine a file multipart (e se ha senso)
		// uDto.setImg(u.getImg());
		return uDto;
	}

}
