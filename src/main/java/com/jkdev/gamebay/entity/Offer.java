package com.jkdev.gamebay.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offers")
@Proxy(lazy=false)
@org.springframework.security.core.Transient
public class Offer {

    public Offer(String title, String game_key, Integer price){
        this.title = title;
        this.gameKey = game_key;
        this.price = price;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "gamekey")
    String gameKey;

    @Column(name = "price")
    Integer price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    User user;

    @Transient
    public void setUser(User user) {
        this.user = user;
    }

}
