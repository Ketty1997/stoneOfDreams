package com.example.demo.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;

public class UserDto {
	private int id;
	private String nome;

    @NotEmpty(message = "The username is required")
	private String email;
	
    
	private String password;
	
	private LocalDate dataNascita;
	  private String formattedDataNascita;
	

	private String segnoZodiacale;

	
	private MultipartFile img;
	
	

	
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
}
