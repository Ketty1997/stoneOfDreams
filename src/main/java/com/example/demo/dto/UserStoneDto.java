package com.example.demo.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;



public class UserStoneDto {
	
	private int id;
	private int userId;
	private int stoneId;
	private LocalDate data;
	private String note;
	private MultipartFile img;
	private String imagePath;
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public MultipartFile getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		this.img = img;
	}

	public UserStoneDto () {}
	
	public UserStoneDto(int id) {
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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	

}
