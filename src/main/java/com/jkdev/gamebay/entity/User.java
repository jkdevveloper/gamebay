package com.jkdev.gamebay.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User implements UserDetails{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Game> games;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Game> cart;

    @Column(name = "coinbalance")
    private Integer coinBalance;

    @Column(name = "username")
    @Size(min = 0, max = 14)
    private String username;


    /*@Column(name = "email")
    @Size(min = 0, max = 14)
    private String email;*/

    @Column(name = "password")
    @Size(min = 0, max = 14)
    private String password;

    public void addGame(Game game) {
        if (this.games == null) {
            this.games = new ArrayList<>();
        }
        game.setUser(this);
        games.add(game);
    }

    public void addGameToCart(Game game){
        if(this.cart == null){
            this.cart = new ArrayList<>();
        }
        this.cart.add(game);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        return grantedAuthorities;
    }
    @Override
    public String getUsername(){
        return this.username;
    }
    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString(){
        return this.id + this.username + this.password;
    }
}
