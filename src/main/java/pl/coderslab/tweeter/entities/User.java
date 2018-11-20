package pl.coderslab.tweeter.entities;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity(name = "users")
public class User {

    // == fields ==

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Field cannot be empty")
    private String username;

    @NotEmpty(message = "Field cannot be empty")
    private String password;

    private boolean enabled;

    @NotEmpty(message = "Field cannot be empty")
    @Email
    private String email;

    // == constructors ==

    public User() {};

    public User(@NotEmpty String username, @NotEmpty String password, @NotEmpty boolean enabled, @NotEmpty @Email String email) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
    }

    //GETTERS_AND_SETTERS
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {this.password = BCrypt.hashpw(password, BCrypt.gensalt());}
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
