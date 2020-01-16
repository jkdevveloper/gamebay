package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.Game;
import com.jkdev.gamebay.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();

    void saveUser(User user);

    User getUser(Long id);

    void deleteUser(Long id);

    void updateUser(User place);

    void addGameToUser(Long id, Game game);

    void addCoins(Long userId, Integer amount);

    void removeCoins(Long userId, Integer amount);

    User findByUserName(String username);
}
