package com.jkdev.gamebay.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

import static javax.persistence.CascadeType.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")

public class User implements UserDetails{

    public User(Integer coinBalance, String username, String password) {
        this.coinBalance = coinBalance;
        this.username = username;
        this.password = password;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade={MERGE, REMOVE, REFRESH, DETACH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Game> cart;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade={MERGE, REFRESH, DETACH})
    @JsonManagedReference
    private List<Offer> offers;

    @OneToMany(mappedBy = "seller", cascade={MERGE, REFRESH, DETACH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user", cascade={MERGE, REFRESH, DETACH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<OwnedKey> ownedKeys;

    @Column(name = "coinbalance")
    private Integer coinBalance = 0;

    @Column(name = "username")
    @Size(min = 0, max = 14)
    private String username;

    @Column(name = "password")
    @Size(min = 0, max = 14)
    private String password;

    public void addOffer(Offer offer) {
        if (this.offers == null) {
            this.offers = new ArrayList<>();
        }
        offer.setUser(this);
        offers.add(offer);
    }

    public void addGameToCart(Game game){
        if(this.cart == null){
            this.cart = new ArrayList<>();
        }
        game.setUser(this);
        this.cart.add(game);
    }

    public void addTransaction(Transaction transaction){
        if(this.transactions == null){
            this.transactions = new ArrayList<>();
        }
        transaction.setSeller(this);
        this.transactions.add(transaction);
    }

    public void addGameToCollection(OwnedKey ownedKey){
        if(this.ownedKeys == null){
            this.ownedKeys = new ArrayList<>();
        }
        ownedKey.setUser(this);
        this.ownedKeys.add(ownedKey);
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
}
