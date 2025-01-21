package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

public class StoneDto {
	
	private int id;
	private String nome;
	private String tipo;
	private String colore;
	private String elemento;
	private String emozione;
	private String descrizione;
	private String immagine;
	private MultipartFile immagineFile; 
	
	public MultipartFile getImmagineFile() {
		return immagineFile;
	}

	public void setImmagineFile(MultipartFile immagineFile) {
		this.immagineFile = immagineFile;
	}

	public StoneDto() {}
	
	public StoneDto (int id) {
		this.id= id;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getElemento() {
		return elemento;
	}

	public void setElemento(String elemento) {
		this.elemento = elemento;
	}

	public String getEmozione() {
		return emozione;
	}

	public void setEmozione(String emozione) {
		this.emozione = emozione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	
	

}
