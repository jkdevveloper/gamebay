package com.jkdev.gamebay.controller;

import com.jkdev.gamebay.entity.*;
import com.jkdev.gamebay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private IUserService userService;
    private IGameService gameService;
    private ITransactionService transactionService;
    private IOfferService offerService;
    private Validator validator;
    private IOwnedKeyService ownedKeyService;
    private AuthenticationManager authManager;

    public UserController(@Autowired IUserService userService,
                          @Autowired IGameService gameService,
                          @Autowired Validator validator,
                          @Autowired ITransactionService transactionService,
                          @Autowired IOfferService offerService,
                          @Autowired IOwnedKeyService ownedKeyService,
                          @Lazy
                          @Autowired AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.gameService = gameService;
        this.validator = validator;
        this.transactionService = transactionService;
        this.offerService = offerService;
        this.ownedKeyService = ownedKeyService;
        this.authManager = authenticationManager;
    }


    @PostMapping("/search")
    public String afterSearch(@ModelAttribute("search") String search_string, Model model){
        List<Offer> offers = offerService.getOffers();
        List<Offer> result_list = new ArrayList<>();
        for(Offer o : offers){
            if(o.getTitle().toLowerCase().contains(search_string.toLowerCase()) && o.getVisible()){
                result_list.add(o);
            }
        }
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", this.userService.findByUserName(u.getUsername()));
        model.addAttribute("results", result_list);

        return "results";
    }

    @GetMapping("/search")
    public String searchResults(@ModelAttribute("results") List<Offer> results, Model model) {

        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", this.userService.findByUserName(u.getUsername()));
        model.addAttribute("results", results);


        return "results";
    }

    @GetMapping("/index")
    public String index(Model model) {

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            model.addAttribute("games", this.offerService.getOffers());
            return "index";
        } else {
            org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", this.userService.findByUserName(u.getUsername()));
            User loggedUser = this.userService.findByUserName(u.getUsername());
            List<Offer> displayedOffers = new ArrayList<>();
            for(Offer o : this.offerService.getOffers()){
                if(!o.getUser().getId().equals(loggedUser.getId()) && o.getVisible()){
                    displayedOffers.add(o);
                }
            }
            model.addAttribute("games", displayedOffers);
            return "indexl";
        }
    }

    @GetMapping("/transactions")
    public String transactions(Model model) {
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", this.userService.findByUserName(u.getUsername()));
        return "transactions";
    }

    @PostMapping("/removeFromCart")
    public String removeFromCart(Model model, @RequestParam Long id){
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.findByUserName(u.getUsername());
        Game g = this.gameService.getGame(id);
        //TODO nie działa usuwanie z koszyka, przywraca o.visible ale nie wywala z koszyka xd

        Offer o = this.offerService.getOfferById(id);
        o.setVisible(true);
        this.offerService.saveOffer(o);

        this.gameService.deleteGame(g.getId());

        this.userService.saveUser(user);


        model.addAttribute("user", this.userService.findByUserName(u.getUsername()));

        return "redirect:/cart";

    }

    @GetMapping("/game")
    public String gameInfo(Model model, @RequestParam Long id){
        //TODO dodawać dwa randomowe tytuły RÓŻNE od tego który wyświetlamy
        //TODO w przypadku usera zalogowanego, nie może to być żadna wystawiona przez niego oferta
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            model.addAttribute("game", this.offerService.getOfferById(id));
            return "game";
        } else {
            org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", this.userService.findByUserName(u.getUsername()));
            model.addAttribute("game", this.offerService.getOfferById(id));
            return "gamel";
        }
    }
    @PostMapping("/addToCart")
    public String addToCart(Model model, @RequestParam Long id){
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.findByUserName(u.getUsername());
        Offer o = this.offerService.getOfferById(id);
        o.setVisible(false);

        Game g = new Game(o.getId(), o.getTitle(), o.getGameKey(), o.getPrice());

        user.addGameToCart(g);
        this.offerService.saveOffer(o);
        this.gameService.saveGame(g);
        this.userService.saveUser(user);
        User us = this.userService.findByUserName(u.getUsername());
        model.addAttribute("user", us);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", this.userService.findByUserName(u.getUsername()));

        Integer sum = 0;
        for(Game g : userService.findByUserName(u.getUsername()).getCart()){
            sum+=g.getPrice();
        }
        model.addAttribute("sum", sum);
        return "cart";
    }

    @PostMapping("/buyGames")
    public String buyGames(Model model){
        //TODO WYSYŁANIE MAILA Z KODEM GRY
        //TODO nie dodaje gościowi co gierke sprzedaje kasy XDD
        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUserName(u.getUsername());
        List<Game> gamesToDelete = new ArrayList<>();
        Integer sum = 0;
        for(Game g : user.getCart()){
            sum+=g.getPrice();
        }

        if(user.getCoinBalance() > sum){
            user.setCoinBalance(user.getCoinBalance() - sum);
            for(Game g : userService.findByUserName(u.getUsername()).getCart()){
                OwnedKey ownedKey = new OwnedKey(g.getTitle(), g.getGame_key());
                user.addGameToCollection(ownedKey);
                ownedKeyService.saveOwnedKey(ownedKey);
                gamesToDelete.add(g);
                long millis=System.currentTimeMillis();
                Transaction t = new Transaction(g.getPrice(), new Date(millis), offerService.getOfferById(g.getId()).getUser().getId());
                offerService.deleteOffer(g.getId());
                user.addTransaction(t);
                transactionService.saveTransaction(t);
            }
            for(Game game : gamesToDelete){
                this.gameService.deleteGame(game.getId());
            }
        }

        this.userService.saveUser(user);
        user = userService.findByUserName(u.getUsername());
        model.addAttribute("user", user);
        return "redirect:/collection";
    }

    @GetMapping("/collection")
    public String collection(Model model){
        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.findByUserName(u.getUsername());
        model.addAttribute("user", user);

        return "collection";
    }

    @GetMapping("/addCoins")
    public String addCoins(Model model){
        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.findByUserName(u.getUsername());
        model.addAttribute("user", user);
        return "doladowanie";
    }

    @PostMapping("/addCoins")
    public String coinsAdded(@RequestParam("amount") Integer amount, Model model){
        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.findByUserName(u.getUsername());
        if(amount < 10000) {
            Integer balance = user.getCoinBalance();

            user.setCoinBalance(balance + amount);

            this.userService.saveUser(user);
            model.addAttribute("user", this.userService.findByUserName(u.getUsername()));
            return "redirect:/panel";

        }else
            model.addAttribute("user", this.userService.findByUserName(u.getUsername()));
            model.addAttribute("gcoinError", " ");
            return "doladowanie";

    }

    @GetMapping("/newoffer")
    public String addOffer(Model model){
        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", this.userService.findByUserName(u.getUsername()));
        model.addAttribute("offer", new Offer());

        return "newoffer";
    }

    @PostMapping("/newoffer")
    public String afterAddOffer(@ModelAttribute Offer offer, Model model){
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.findByUserName(u.getUsername());
        model.addAttribute("user", user);
        offer.setPrice((int) (offer.getPrice() + offer.getPrice() * 0.1));
        user.addOffer(offer);
        this.offerService.saveOffer(offer);
        this.userService.saveUser(user);

        return "offers";
    }

    @GetMapping("/offers")
    public String offers(Model model) {
        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", this.userService.findByUserName(u.getUsername()));

        return "offers";
    }

    @GetMapping("/sum")
    public String summary(Model model){
        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", this.userService.findByUserName(u.getUsername()));

        return "sum";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/panel")
    public String userPanel(Model model) {
        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.findByUserName(u.getUsername()));
        return "panel";
    }

    @PostMapping("/changePassword")
    public String passwordChanged(Model model, @ModelAttribute("password") String password,
                                  @ModelAttribute("confirmation") String passwordConfirmation){

        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.findByUserName(u.getUsername()));

        User user = userService.findByUserName(u.getUsername());

        if(password.equals(passwordConfirmation)){
            user.setPassword(password);
            this.userService.saveUser(user);
            return "redirect:/panel";
        }else
            model.addAttribute("passwordError", " ");
            return "panel";

    }

    @PostMapping("/changeEmail")
    public String emailChanged(Model model, @ModelAttribute("email") String email,
                                  @ModelAttribute("emailConfirm") String emailConfirmation){

        org.springframework.security.core.userdetails.User u =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.findByUserName(u.getUsername()));

        User user = userService.findByUserName(u.getUsername());

        if(email.equals(emailConfirmation)){
            user.setEmail(email);
            this.userService.saveUser(user);
            return "redirect:/panel";
        }else
            model.addAttribute("error", "Adresy e-mail muszą się zgadzać!");
        return "panel";

    }


    @GetMapping("/register")
    public String afterRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute User user, Model model) {
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (constraintViolations.size() == 0 && this.userService.findByUserName(user.getUsername()) == null &&
        this.userService.findByEmail(user.getEmail()) == null) {

            this.userService.saveUser(user);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
                    (new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities()), user.getPassword(), user.getAuthorities());

            authManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            return "redirect:/panel";
        }else
            model.addAttribute("error", " ");
            return "register";


    }
}
