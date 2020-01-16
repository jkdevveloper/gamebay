package com.jkdev.gamebay.controller;

import com.jkdev.gamebay.entity.Game;
import com.jkdev.gamebay.entity.User;
import com.jkdev.gamebay.service.IGameService;
import com.jkdev.gamebay.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Controller
public class UserController {

    private IUserService userService;
    private IGameService gameService;
    private Validator validator;


    public UserController(@Autowired IUserService userService,
                          @Autowired IGameService gameService,
                          @Autowired Validator validator) {
        this.userService = userService;
        this.gameService = gameService;
        this.validator = validator;
    }



    @GetMapping("/index")
    public String index(Model model){

        model.addAttribute("games", this.gameService.getGames());

        return "index";
    }
    @GetMapping("/panel")
    public String userPanel(Model model){

        model.addAttribute("user", new User());
        return "panel";
    }
    @GetMapping("/transactions")
    public String transactions(Model model){

        model.addAttribute("user", new User());
        return "transactions";
    }
    @GetMapping("/offers")
    public String offers(Model model){

        model.addAttribute("user", new User());
        return "offers";
    }
    @GetMapping("/settings")
    public String settings(Model model){
        model.addAttribute("user", new User());
        return "settings";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }

    @PostMapping("/login")
    public String userLogged(){
        return "redirect:/panel";
    }

    @GetMapping("/register")
    public String afterRegister(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute User user){
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if(constraintViolations.size() == 0){
            this.userService.saveUser(user);
        }

        return "sum";
    }
}
