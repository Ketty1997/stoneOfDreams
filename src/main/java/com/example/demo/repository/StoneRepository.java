package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Stone;

public interface StoneRepository extends JpaRepository <Stone, Integer> {
	Stone findByNome(String nome);
}
