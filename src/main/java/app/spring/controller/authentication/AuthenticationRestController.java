package app.spring.controller.authentication;

import app.spring.payload.authentication.LoginDto;
import app.spring.payload.authentication.RegisterDto;
import app.spring.response.JWTAuthResponse;
import app.spring.service.interfaces.authentication.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationRestController {

    private final IAuthenticationService authenticationService;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public AuthenticationRestController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid login credentials");
        }
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setToken(authenticationService.login(loginDto));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).map(roleName -> roleName.substring(5)).collect(Collectors.joining(","));
            jwtAuthResponse.setRoles(roles);
        }

        jwtAuthResponse.setLastLogin(new Date());
        jwtAuthResponse.setExpireDate(new Date(new Date().getTime() + jwtExpirationDate));

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authenticationService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
