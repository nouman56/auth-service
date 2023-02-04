package com.acserita.authservice.controller;

import com.acserita.authservice.util.AuthUtil;
import com.acserita.authservice.dto.JwtResponse;
import com.acserita.authservice.dto.UsersDTO;
import com.acserita.authservice.entities.Users;
import com.acserita.authservice.repository.UsersRepository;
import com.acserita.authservice.service.AuthService;
import com.acserita.authservice.util.UserDetailImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUtil jwtTokenUtil;

    @Autowired
    private UserDetailImp userDetailImp;

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody UsersDTO usersDTO) throws Exception {
        authService.signup(usersDTO);
        final UserDetails userDetails = userDetailImp.loadUserByUsername(usersDTO.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UsersDTO usersDTO) throws Exception {
        authenticate(usersDTO.getUsername(), usersDTO.getPassword());

        final UserDetails userDetails = userDetailImp.loadUserByUsername(usersDTO.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }



    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
