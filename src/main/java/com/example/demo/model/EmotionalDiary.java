package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EmotionalDiary implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;
	
	private LocalDate data;
	private String statoAnimo;
	
	public EmotionalDiary() {}
	
	public EmotionalDiary(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
