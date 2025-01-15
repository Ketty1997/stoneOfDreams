package com.example.demo.dto;

import java.time.LocalDate;

public class SuggestionsDto {
	
	private int id;
	private int userId;
	private int stoneId;
	private LocalDate dataSuggerimento;
	private String motivo;
	
	public SuggestionsDto () {}
	
	public SuggestionsDto(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStoneId() {
		return stoneId;
	}

	public void setStoneId(int stoneId) {
		this.stoneId = stoneId;
	}

	public LocalDate getDataSuggerimento() {
		return dataSuggerimento;
	}

	public void setDataSuggerimento(LocalDate dataSuggerimento) {
		this.dataSuggerimento = dataSuggerimento;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	

}
