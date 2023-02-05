package com.acsertia.authservice.controller;

import com.acsertia.authservice.util.AuthUtil;
import com.acsertia.authservice.dto.JwtResponse;
import com.acsertia.authservice.dto.UsersDTO;
import com.acsertia.authservice.repository.UsersRepository;
import com.acsertia.authservice.service.AuthService;
import com.acsertia.authservice.util.UserDetailImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
