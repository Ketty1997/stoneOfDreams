package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UserStone;

public interface UserStoneRepository extends JpaRepository <UserStone, Integer> {

}
