package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;

public class StoneDto {
	
	private int id;

	@NotEmpty(message = "The name is required")
	private String nome;

	private String tipo;

	private String colore;
	private String elemento;
	private String emozione;

	@NotEmpty(message = "The description is required")
	private String descrizione;

	private String imgName;
	private MultipartFile immagineFile; 
	private String storiaPietra;
	private String benefici;



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
		

	
	
	public MultipartFile getImmagineFile() {
		return immagineFile;
	}

	public void setImmagineFile(MultipartFile immagineFile) {
		this.immagineFile = immagineFile;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getStoriaPietra() {
		return storiaPietra;
	}

	public void setStoriaPietra(String storiaPietra) {
		this.storiaPietra = storiaPietra;
	}

	

	public String getBenefici() {
		return benefici;
	}

	public void setBenefici(String benefici) {
		this.benefici = benefici;
	}
	

}
