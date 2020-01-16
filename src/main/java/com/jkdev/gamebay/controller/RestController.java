package com.jkdev.gamebay.controller;

import com.jkdev.gamebay.entity.User;
import com.jkdev.gamebay.service.IGameService;
import com.jkdev.gamebay.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Validator;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private IUserService userService;
    private IGameService gameService;
    private Validator validator;


    public RestController(@Autowired IUserService userService,
                          @Autowired IGameService gameService,
                          @Autowired Validator validator) {
        this.userService = userService;
        this.gameService = gameService;
        this.validator = validator;
    }

    @GetMapping("/getUser/{username}")
    public User getUser(@PathVariable String username){
        return this.userService.findByUserName(username);
    }
}
