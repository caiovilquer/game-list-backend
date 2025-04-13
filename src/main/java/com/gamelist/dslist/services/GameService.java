package com.gamelist.dslist.services;

import java.util.List;
import java.util.Optional;

import com.gamelist.dslist.entities.Belonging;
import com.gamelist.dslist.entities.GameList;
import com.gamelist.dslist.repositories.BelongingRepository;
import com.gamelist.dslist.repositories.GameListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamelist.dslist.dto.GameDTO;
import com.gamelist.dslist.dto.GameMinDTO;
import com.gamelist.dslist.entities.Game;
import com.gamelist.dslist.projections.GameMinProjection;
import com.gamelist.dslist.repositories.GameRepository;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private BelongingRepository belongingRepository;
	@Autowired
	private GameListRepository gameListRepository;

	@Transactional(readOnly = true)
	public List<GameMinDTO> findAll(){
		List<Game> result = gameRepository.findAll();
		return  result.stream().map(x -> new GameMinDTO(x)).toList();
	}
	
	@Transactional(readOnly = true)
	public GameDTO findById(Long Id) {
		Game result = gameRepository.findById(Id).get();
		return new GameDTO(result);
	}
	
	@Transactional(readOnly = true)
	public List<GameMinDTO> findByList(Long ListId){
		List<GameMinProjection> result = gameRepository.searchByList(ListId);
		return  result.stream().map(x -> new GameMinDTO(x)).toList();
	}

	@Transactional
	public GameDTO insert(GameDTO dto){
		Game entity = new Game();
		copyDtoToEntity(dto, entity);
		entity = gameRepository.save(entity);
		Optional<GameList> list = gameListRepository.findById(dto.getListId());
		if (list.isPresent()) {
			Integer maxPosition = belongingRepository.findMaxPositionByListId(list.get().getId());
			int newPosition = (maxPosition != null ? maxPosition : 0) + 1;
			Belonging belonging = new Belonging(entity, list.get(), newPosition);
			belongingRepository.save(belonging);
		}
		return new GameDTO(entity);
	}

	@Transactional
	public void deleteById(Long id) {
			// Primeiro, remova todas as referências deste jogo na tabela TB_BELONGING
			belongingRepository.deleteByGameId(id);

			// Agora é seguro deletar o jogo
			gameRepository.deleteById(id);
	}

	private void copyDtoToEntity(GameDTO dto, Game entity) {
		entity.setTitle(dto.getTitle());
		entity.setYear(dto.getYear());
		entity.setGenre(dto.getGenre());
		entity.setPlatform(dto.getPlatform());
		entity.setScore(dto.getScore());
		entity.setImgUrl(dto.getImgUrl());
		entity.setShortDescription(dto.getShortDescription());
		entity.setLongDescription(dto.getLongDescription());
	}
}
