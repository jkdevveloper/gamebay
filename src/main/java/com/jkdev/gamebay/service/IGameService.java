package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.Game;


import java.util.List;

public interface IGameService {

    List<Game> getGames();

    void saveGame(Game game);

    Game getGame(Long id);

    void deleteGame(Long id);

    void updateGame(Game game);

    void deleteGame(Game game);


}
