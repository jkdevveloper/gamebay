package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.Game;
import com.jkdev.gamebay.entity.User;
import com.jkdev.gamebay.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return this.userRepository.getOne(id);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void addGameToUser(Long id, Game game) {
        Optional<User> result = this.userRepository.findById(id);
        if (result.isPresent()) {
            User tempUser = result.get();
            //tempUser.addGame(game);
            this.userRepository.save(tempUser);
        } else
            throw new RuntimeException("User not found");

    }

    @Override
    public void addCoins(Long userId, Integer amount) {
        Optional<User> result = this.userRepository.findById(userId);
        if (result.isPresent()) {
            User tempUser = result.get();
            if (tempUser.getCoinBalance() == null) {
                tempUser.setCoinBalance(0);
            }
            tempUser.setCoinBalance(tempUser.getCoinBalance() + amount);
            this.userRepository.save(tempUser);
        } else
            throw new RuntimeException("User not found");
    }

    @Override
    public void removeCoins(Long userId, Integer amount) {
        Optional<User> result = this.userRepository.findById(userId);
        if (result.isPresent()) {
            User tempUser = result.get();
            if (tempUser.getCoinBalance() == null) {
                tempUser.setCoinBalance(0);
            }
            //TODO if amount to remove > total user amount -> exception
            tempUser.setCoinBalance(tempUser.getCoinBalance() - amount);
            this.userRepository.save(tempUser);
        } else
            throw new RuntimeException("User not found");
    }



    @Override
    public void addToCart(Game game, User user) {
        User u = this.userRepository.findByUsername(user.getUsername());
        u.addGameToCart(game);
        this.userRepository.save(u);
    }

    @Override
    public User findByUserName(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
