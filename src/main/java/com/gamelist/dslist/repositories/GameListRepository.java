package com.gamelist.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gamelist.dslist.entities.GameList;

public interface GameListRepository  extends JpaRepository<GameList, Long>{
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE tb_belonging SET position = :newPosition WHERE list_id = :listId AND game_id = :gameId")
	void updateBelongingPosition(Long listId, Long gameId, Integer newPosition);

	@Modifying
	@Query(nativeQuery = true, value = "INSERT INTO tb_belonging (list_id, game_id) VALUES (:listId, :gameId);")
	void insertBelonging(Long listId, Long gameId);
}
