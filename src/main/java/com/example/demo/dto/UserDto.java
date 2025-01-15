package com.example.demo.dto;

import java.time.LocalDate;

public class UserDto {
	private int id;
	private String nome;
	private String email;
	private String password;
	private LocalDate dataNascita;
	private String segnoZodiacale;
	
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
	
	

}
