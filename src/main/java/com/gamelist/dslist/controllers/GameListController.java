package com.gamelist.dslist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamelist.dslist.dto.GameDTO;
import com.gamelist.dslist.dto.GameMinDTO;
import com.gamelist.dslist.services.GameService;

@RestController
@RequestMapping(value = "/games")
public class GameListController {
	
	@Autowired
	private GameService gameService;
	
	@GetMapping
	public List<GameMinDTO> findAll(){
		return gameService.findAll();
	}
	
	@GetMapping(value = "/{Id}")
	public GameDTO findById(@PathVariable Long Id){
		return gameService.findById(Id);
	}
}
