package com.blood.blood_sample_system.service;
import com.blood.blood_sample_system.dto.AuthDTO.*;
import com.blood.blood_sample_system.entity.User;
import com.blood.blood_sample_system.repository.UserRepository;
import com.blood.blood_sample_system.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    //register
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        //create new userobj
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .phone(request.getPhone())
                .role(User.Role.USER)
                .build();
        //save user to db
        userRepository.save(user);
        //generate jwt token
        String token = jwtUtil.generateToken(user.getEmail());

//send response
        return new AuthResponse(
                token,
                user.getName(),
                user.getEmail(),
                user.getRole().name()

        );
    }
//login
public AuthResponse login(LoginRequest request){
    //check email and pwd
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()));

    //get user from the db
   User user= userRepository.findByEmail(request.getEmail())
                    .orElseThrow(()->new RuntimeException("User not found!"));
    //generate the token
    String token=jwtUtil.generateToken(user.getEmail());

    //send back response
    return new AuthResponse(
            token,
            user.getName(),
            user.getEmail(),
            user.getRole().name()
    );
}
}