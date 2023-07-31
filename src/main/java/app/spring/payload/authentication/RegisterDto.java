package app.spring.payload.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private long id;
    @NotEmpty(message = "Name should not be null.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Invalid name format, please use only letters.")
    private String name;

    @NotEmpty(message = "Username should not be null.")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Invalid username format, please use only letters, digits and underscore.")
    private String username;

    @NotEmpty(message = "Email should not be null.")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password should not be null.")
    @Size(min = 8, max = 24, message = "Password should contain at least 8 characters, but no more than 24 characters.")
    private String password;

    @JsonProperty("confirm_password")
    @NotEmpty(message = "Confirm password should not be null.")
    @Size(min = 8, max = 24, message = "Confirm password should contain at least 8 characters, but no more than 24 characters.")
    private String confirmPassword;

    private String activateCode = UUID.randomUUID().toString();
    private Boolean isEnabled = false;

}
