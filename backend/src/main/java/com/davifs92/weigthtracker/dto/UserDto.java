package com.davifs92.weigthtracker.dto;

import com.davifs92.weigthtracker.entities.Weight;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class UserDto implements Serializable {
    private String id;
    @NotBlank(message = "Name should not be empty")
    private String name;
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @Min(value = 16, message = "Age should not be less than 16")
    @Max(value = 99, message = "Age should not be greater than 99")
    private Integer age;
    @NotBlank(message = "Height should not be empty")
    private float height;
    @NotNull(message = "Goal should not be empty")
    private float goal;

    private Instant createdDate;
    private List<WeightDto> weights = new ArrayList<>();


    public UserDto(String id, String name, String password, String username, String email, Integer age, float height, float goal, Instant createdDate, List<WeightDto> weights) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.age = age;
        this.height = height;
        this.goal = goal;
        this.createdDate = createdDate;
        this.weights = weights;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setGoal(float goal) {
        this.goal = goal;
    }

    public List<WeightDto> getWeights() {
        return weights;
    }

    public void setWeights(List<WeightDto> weights) {
        this.weights = weights;
    }
}
