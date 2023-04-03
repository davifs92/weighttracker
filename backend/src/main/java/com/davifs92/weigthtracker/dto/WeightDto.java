package com.davifs92.weigthtracker.dto;

import com.davifs92.weigthtracker.entities.User;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@Getter
public class WeightDto {
    private Long id;
    private Integer weight;
    private Instant date;
    private User user;

    public WeightDto(Long id, Integer weight, Set<User> userSet) {
        this.id = id;
        this.weight = weight;
        this.date = Instant.now();
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDate(){
        this.date = Instant.now();
    }
}
