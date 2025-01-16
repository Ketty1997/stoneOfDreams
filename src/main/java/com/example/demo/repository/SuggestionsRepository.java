package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Suggestions;

public interface SuggestionsRepository extends JpaRepository <Suggestions, Integer> {

}
