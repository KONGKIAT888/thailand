package app.spring.service.implement.authentication;

import app.spring.components.JwtTokenProvider;
import app.spring.entity.authentication.Role;
import app.spring.entity.authentication.User;
import app.spring.exception.APIException;
import app.spring.payload.authentication.LoginDto;
import app.spring.payload.authentication.RegisterDto;
import app.spring.repository.authentication.RoleRepository;
import app.spring.repository.authentication.UserRepository;
import app.spring.service.interfaces.authentication.IAuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    public AuthenticationService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 UserDetailsService userDetailsService,
                                 JwtTokenProvider jwtTokenProvider,
                                 ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.modelMapper = modelMapper;
    }

    public String login(LoginDto loginDto) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsernameOrEmail());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDetails, loginDto.getPassword(), userDetails.getAuthorities()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenProvider.generateToken(authentication);
        } catch (BadCredentialsException ex) {
            throw new IllegalArgumentException("Invalid password");
        } catch (UsernameNotFoundException ex) {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        String password = registerDto.getPassword();
        String confirmPassword = registerDto.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Password and Confirm Password do not match.");
        }

        User user = mapToEntity(registerDto);
        user.setPassword(passwordEncoder.encode(password));
        user.setConfirmPassword(passwordEncoder.encode(confirmPassword));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        User newUser = userRepository.save(user);

        return "User registered successfully!.";
    }

    private RegisterDto mapToDTO(User user) {
        return modelMapper.map(user, RegisterDto.class);
    }

    private User mapToEntity(RegisterDto registerDto) {
        return modelMapper.map(registerDto, User.class);
    }

}
