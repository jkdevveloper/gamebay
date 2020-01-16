package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.Game;
import com.jkdev.gamebay.repository.IGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements IGameService {

    IGameRepository gameRepository;

    GameServiceImpl(@Autowired IGameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> getGames() {
        return this.gameRepository.findAll();
    }

    @Override
    public void saveGame(Game game) {
        this.gameRepository.save(game);
    }

    @Override
    public Game getGame(Long id) {
        return this.gameRepository.getOne(id);
    }

    @Override
    public void deleteGame(Long id) {
        this.gameRepository.deleteById(id);
    }

    @Override
    public void updateGame(Game game) {
        this.gameRepository.save(game);
    }
}
