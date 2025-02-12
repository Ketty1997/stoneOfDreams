package com.example.demo.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotEmpty;

// @Component
public class UserDto {

	private int id;

	@NotEmpty(message = "Il nome deve essere presente")
	private String nome;

	@NotEmpty(message = "L'email deve essere presente")
	private String email;
	
	private String password;
	
	private LocalDate dataNascita;
	private String formattedDataNascita;
	
	
    @NotEmpty(message = "Inserisci il tuo segno zodiacale")
	private String segnoZodiacale;
	
	
	private MultipartFile img;
	
	private String oldPassword;
	private String confermaPass;
	


	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public UserDto() {}
	
	public UserDto(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getSegnoZodiacale() {
		return segnoZodiacale;
	}

	public void setSegnoZodiacale(String segnoZodiacale) {
		this.segnoZodiacale = segnoZodiacale;
	}
	

	public MultipartFile getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		this.img = img;
	}
	
	public String getFormattedDataNascita() {
		return formattedDataNascita;
	}

	public void setFormattedDataNascita(String formattedDataNascita) {
		this.formattedDataNascita = formattedDataNascita;
	}

	public String getConfermaPass() {
		return confermaPass;
	}

	public void setConfermaPass(String confermaPass) {
		this.confermaPass = confermaPass;
	}
}
