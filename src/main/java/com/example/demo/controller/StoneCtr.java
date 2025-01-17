package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.StoneDto;
import com.example.demo.services.StoneService;


@Controller
@RequestMapping("stone")
public class StoneCtr {
	@Autowired
	private StoneService stoneService;
	
	@GetMapping({"","/"})
	public String listaPietre(Model model) {
		List<StoneDto> listaPietre = stoneService.getListaPietre();
		model.addAttribute("listaPietre", listaPietre);
		return "stones";
	}
	
	@GetMapping("/delete/{id}")
	public String elimina(@PathVariable int id) {
		stoneService.eliminaPietra(id);
		return"success";
	}

}
