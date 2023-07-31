package app.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponse {
    
    private String token;
    private String type = "Bearer";
    @JsonProperty("last_login")
    private Date lastLogin;
    @JsonProperty("expiration_date")
    private Date expireDate;
    private String roles;

}
