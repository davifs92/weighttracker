package com.davifs92.weigthtracker.dto;

import com.davifs92.weigthtracker.entities.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@Getter
public class WeightDto implements Serializable {
    private Long id;
    @Min(value = 10, message = "Weight should not be less than 10")
    @Max(value = 400, message = "Weight should not be bigger than 400")
    @NotBlank(message = "Weight should not be blank")
    private Integer weight;
    @PastOrPresent(message = "Date cannot be on future")
    private Instant date;
    @NotBlank(message = "User cannot be blank")
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
