package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.Game;
import com.jkdev.gamebay.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();

    void saveUser(User user);

    User getUser(Long id);

    void deleteUser(Long id);

    void updateUser(User user);

    void addGameToUser(Long id, Game game);

    void addCoins(Long userId, Integer amount);

    void removeCoins(Long userId, Integer amount);

    void changeGameFromUserToUser(Game game, User seller, User buyer);

    void addToCart(Game game, User user);

    User findByUserName(String username);
}
