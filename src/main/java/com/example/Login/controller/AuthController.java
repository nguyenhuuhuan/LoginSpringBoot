package com.example.Login.controller;

import com.example.Login.entity.AppRole;
import com.example.Login.entity.AppUser;
import com.example.Login.entity.RoleName;
import com.example.Login.exception.AppException;
import com.example.Login.payload.ApiResponse;
import com.example.Login.payload.JwtAuthenticationResponse;
import com.example.Login.payload.LoginRequest;
import com.example.Login.payload.SignUpRequest;
import com.example.Login.repository.RoleRepository;
import com.example.Login.repository.UserRepository;
import com.example.Login.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            System.out.println(jwt);
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        try {
            if (userRepository.existsByUserName(signUpRequest.getUsername())) {
                return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return new ResponseEntity(new ApiResponse(false, "Email is already taken!"), HttpStatus.BAD_REQUEST);
            }
            AppUser appUser = new AppUser(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(), true);
            appUser.setEncryptedPassword(passwordEncoder.encode(appUser.getEncryptedPassword()));

            AppRole appRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));

            appUser.setRoles(Collections.singleton(appRole));

//            UserRole userRole = new UserRole();
//            userRole.setAppUser(appUser);
//            userRole.setAppRole(appRole);

            AppUser result = userRepository.save(appUser);
            roleRepository.save(appRole);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("api/users/{username}")
                    .buildAndExpand(result.getUserName()).toUri();

            return ResponseEntity.created(location).body(new ApiResponse(true, "User register successfully!"));
        } catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
