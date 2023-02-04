package com.acserita.authservice.service;

import com.acserita.authservice.dto.UsersDTO;
import com.acserita.authservice.entities.Users;
import com.acserita.authservice.mapper.AuthMaper;
import com.acserita.authservice.repository.UsersRepository;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AuthMaper authMaper;

    public Map<String, String> signup(UsersDTO usersDTO) {
        Users user = authMaper.DtoToEntity(usersDTO);
       user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword()));
        usersRepository.save(user);
        return null;
    }
}
