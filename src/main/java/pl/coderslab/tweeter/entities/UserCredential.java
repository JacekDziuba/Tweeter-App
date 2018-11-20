package pl.coderslab.tweeter.entities;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserCredential {

    // == fields ==

    @NotEmpty(message = "Field cannot be empty")
    @Email
    private String email;

    @NotEmpty(message = "Field cannot be empty")
    private String password;

}
