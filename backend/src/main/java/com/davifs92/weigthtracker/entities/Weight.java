package com.davifs92.weigthtracker.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tb_weight")
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer weight;
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public Integer getWeight() {
        return weight;
    }

    public Instant getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
