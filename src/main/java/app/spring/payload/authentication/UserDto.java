package app.spring.payload.authentication;

import app.spring.entity.authentication.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String name;
    private String username;
    private String email;
    private Set<Role> roles;
    private String resetHash;
    private Date expireDate;
    private String activateCode;
    private Boolean isEnabled;

}
