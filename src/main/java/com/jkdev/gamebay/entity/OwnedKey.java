package com.jkdev.gamebay.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

import static javax.persistence.CascadeType.REMOVE;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ownedkeys")
@Proxy(lazy=false)
@org.springframework.security.core.Transient
public class OwnedKey {

    public OwnedKey(String title, String gameKey){
        this.title = title;
        this.gameKey = gameKey;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "gamekey")
    String gameKey;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    User user;

    @Transient
    public void setUser(User user) {
        this.user = user;
    }

}
