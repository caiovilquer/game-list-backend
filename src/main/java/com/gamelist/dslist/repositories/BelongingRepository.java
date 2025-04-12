package com.gamelist.dslist.repositories;

import com.gamelist.dslist.entities.Belonging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BelongingRepository extends JpaRepository<Belonging, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_belonging WHERE tb_belonging.game_id = :gameId")
    void deleteByGameId(Long gameId);
}
