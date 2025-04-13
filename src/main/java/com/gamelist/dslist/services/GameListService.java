package com.gamelist.dslist.services;

import java.util.List;

import com.gamelist.dslist.entities.Belonging;
import com.gamelist.dslist.entities.Game;
import com.gamelist.dslist.repositories.BelongingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamelist.dslist.dto.GameListDTO;
import com.gamelist.dslist.entities.GameList;
import com.gamelist.dslist.projections.GameMinProjection;
import com.gamelist.dslist.repositories.GameListRepository;
import com.gamelist.dslist.repositories.GameRepository;

@Service
public class GameListService {
	
	@Autowired
	private GameListRepository gameListRepository;
	
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private BelongingRepository belongingRepository;
	
	@Transactional(readOnly = true)
	public List<GameListDTO> findAll(){
		List<GameList> result = gameListRepository.findAll();
		return  result.stream().map(x -> new GameListDTO(x)).toList();
	}
	
	@Transactional
	public void move(Long listId, int sourceIndex, int destinationIndex) {
		
		List<GameMinProjection> list = gameRepository.searchByList(listId);
		
		GameMinProjection obj = list.remove(sourceIndex);
		list.add(destinationIndex, obj);
		
		int min = sourceIndex < destinationIndex ? sourceIndex : destinationIndex;
		int max = sourceIndex < destinationIndex ? destinationIndex : sourceIndex;
		
		for(int i = min; i <= max; i++) {
			gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
		}
		
	}
	public Belonging insertBelonging(Game game, GameList list) {
		// Consulta o máximo valor de position para o GameList específico
		Integer maxPosition = belongingRepository.findMaxPositionByListId(list.getId());
		int newPosition = (maxPosition != null ? maxPosition : 0) + 1;

		Belonging belonging = new Belonging(game, list, newPosition);
		return belongingRepository.save(belonging);
	}
}
