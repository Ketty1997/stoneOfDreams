package com.example.demo.dto;

import java.time.LocalDate;

public class EmotionalDiaryDto {
	
	private int id;
	private int idUser;
	private LocalDate data;
	private String statoAnimo;
	
	public EmotionalDiaryDto() {}
	
	public EmotionalDiaryDto(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getStatoAnimo() {
		return statoAnimo;
	}

	public void setStatoAnimo(String statoAnimo) {
		this.statoAnimo = statoAnimo;
	}
	
	

}
