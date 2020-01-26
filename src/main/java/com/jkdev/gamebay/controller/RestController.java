package com.jkdev.gamebay.controller;

import com.jkdev.gamebay.entity.Game;
import com.jkdev.gamebay.entity.Offer;
import com.jkdev.gamebay.entity.Transaction;
import com.jkdev.gamebay.entity.User;
import com.jkdev.gamebay.service.IGameService;
import com.jkdev.gamebay.service.IOfferService;
import com.jkdev.gamebay.service.ITransactionService;
import com.jkdev.gamebay.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.util.Date;

@RequestMapping("/api")
@org.springframework.web.bind.annotation.RestController
public class RestController {
    private IUserService userService;
    private IGameService gameService;
    @Autowired
    private IOfferService offerService;

    @Autowired
    private ITransactionService transactionService;

    private Validator validator;


    public RestController(@Autowired IUserService userService,
                          @Autowired IGameService gameService,
                          @Autowired Validator validator) {
        this.userService = userService;
        this.gameService = gameService;
        this.validator = validator;
    }

    @PostMapping("/removeGame/{id}")
    public String removeGame(@PathVariable Long id){
        this.gameService.deleteGame(id);

        return "done";
    }

    @GetMapping("/getUser/{username}")
    public User getUser(@PathVariable String username) {
        return this.userService.findByUserName(username);
    }

    @PostMapping("/setgameToUser/{userId}/")
    public String addToCart(@RequestBody Game game, @PathVariable Long userId) {
        User u = this.userService.getUser(userId);
        u.addGameToCart(game);
        this.gameService.saveGame(game);
        this.userService.saveUser(u);

        return "done";
    }

    @GetMapping("/games/{gameId}")
    public Game getGame(@PathVariable Long gameId) {
        return this.gameService.getGame(gameId);
    }



   /* @PostMapping("/addUser/{login}/{password}/{gameName}")
    public String addUserAndGame(@PathVariable String login,
                                 @PathVariable String password,
                                 @PathVariable String gameName) {

        User user = new User(7000, login, password);
      *//*  Game g = new Game("Cyberpunk 2077", "Key", 1000);
        Game g1 = new Game(gameName, "Key", 12400);*//*
        Offer offer = new Offer("Wither", "Gamekey", 1000);
        Date d = new Date();

        Transaction t = new Transaction(1000, d);

        user.addTransaction(t);
        user.addOffer(offer);
        //user.addGameToCart(g);
        //user.addGameToCart(g1);
        offerService.saveOffer(offer);
        //this.gameService.saveGame(g1);
        //this.gameService.saveGame(g);
        this.transactionService.saveTransaction(t);
        this.userService.saveUser(user);

        return "done";
    }

    @PostMapping("/deleteGame/{id}")
    public String deleteGame(@PathVariable Long id){
        this.gameService.deleteGame(id);
        return "works?";
    }*/
}
