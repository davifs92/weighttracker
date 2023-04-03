package com.davifs92.weigthtracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private float height;
    private float goal;

    private Instant createdDate;

    public UserDto(Long id, String name, String email, Integer age, float height, float goal) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.height = height;
        this.goal = goal;
        this.createdDate = Instant.now();
    }

    public void setId(Long id) {
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
}
