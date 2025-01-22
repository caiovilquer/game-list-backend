package com.gamelist.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamelist.dslist.entities.GameList;

public interface GameListRepository  extends JpaRepository<GameList, Long>{

}
