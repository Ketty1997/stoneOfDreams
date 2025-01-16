package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.EmotionalDiary;

public interface EmotionalDiaryRepository extends JpaRepository <EmotionalDiary, Integer> {

}
