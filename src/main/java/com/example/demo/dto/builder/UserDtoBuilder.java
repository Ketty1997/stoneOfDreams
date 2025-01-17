package com.example.demo.dto.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.util.PasswordEncoder;

@Component // -> importante che sia annotata con Component per essere gestita da spring
public class UserDtoBuilder {

	private static PasswordEncoder passwordEncoder;

	//in questo caso non possiamo fare direttamente @Autowired di PasswordEncoder perché dobbiamo usarlo in un metodo statico e darebbe problemi
	// Dobbiamo quindi utilizzare un Setter Statico per Iniettare PasswordEncoder e poterlo usare nel metodo statico
	/*	
		- Spring inietterà il bean PasswordEncoder una volta nel metodo setPasswordEncoder, che assegna il valore alla variabile statica.
		- Successivamente, il metodo statico UserFromDtoToEntity potrà accedere alla variabile passwordEncoder.
 	*/
	@Autowired
	public void setPasswordEncoder(PasswordEncoder encoder) {
		UserDtoBuilder.passwordEncoder = encoder;
	}


	public static User UserFromDtoToEntity (UserDto uDto) {

		//test di verifica per vedere se viene inizializzato l'encoder
		if (passwordEncoder == null) {
			throw new IllegalStateException("PasswordEncoder is not initialized!");
		}
		System.out.println("PasswordEncoder is initialized: " + passwordEncoder.getClass().getName());
	

		//da MultipartFile trasformiamo in stringa per inserirla nel db
		MultipartFile userImage = uDto.getImg();
		String userImageName = userImage.getOriginalFilename();

		User u = new User();
		u.setId(uDto.getId());
		u.setNome(uDto.getNome());
		u.setEmail(uDto.getEmail());
		u.setPassword(passwordEncoder.encode(uDto.getPassword()));
		u.setDataNascita(uDto.getDataNascita());
		u.setSegnoZodiacale(uDto.getSegnoZodiacale());
		u.setImg(userImageName);
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
