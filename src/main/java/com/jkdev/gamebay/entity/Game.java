package com.jkdev.gamebay.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.CascadeType.REMOVE;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "game")
@Proxy(lazy=false)
public class Game {

    public Game(String title, String game_key, Integer price){
        this.title = title;
        this.game_key = game_key;
        this.price = price;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(name = "title")
    String title;

    @NotNull
    @Column(name = "game_key")
    String game_key;

    @NotNull
    @Column(name = "price")
    Integer price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, REMOVE, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    User user;

    @Transient
    public void setUser(User user) {
        this.user = user;
    }

}
