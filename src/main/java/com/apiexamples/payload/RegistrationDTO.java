package com.apiexamples.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data

public class RegistrationDTO {

    private Long id;

    @Size(min = 2,message = "Should be more than 2 characters")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 10,max = 10,message = "Should be 10 digits")
    private String mobile;

    private String message;


}