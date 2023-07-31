package app.spring.service.interfaces.authentication;

import app.spring.payload.authentication.LoginDto;
import app.spring.payload.authentication.RegisterDto;

public interface IAuthenticationService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
