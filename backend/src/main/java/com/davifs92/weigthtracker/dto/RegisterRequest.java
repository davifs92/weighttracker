package com.davifs92.weigthtracker.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String password;
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

}