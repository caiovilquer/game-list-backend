package com.gamelist.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamelist.dslist.entities.Game;

public interface GameRepository extends JpaRepository<Game, Long>{
	
}
