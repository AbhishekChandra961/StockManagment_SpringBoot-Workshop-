package com.bridgelabz.workshop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z]{2,}$", message = "The Name is notEmpty")
    private String firstName;
    private String lastName;
    @NotNull(message = "The email not be  null ")
    private String email;
    @NotNull(message = "The password not be  null ")
    private String password;
}
