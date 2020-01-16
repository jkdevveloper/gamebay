package com.jkdev.gamebay.repository;

import com.jkdev.gamebay.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGameRepository extends JpaRepository<Game, Long> {
}
