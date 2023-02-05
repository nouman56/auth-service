package com.acsertia.authservice.service;

import com.acsertia.authservice.dto.UsersDTO;
import com.acsertia.authservice.entities.Users;
import com.acsertia.authservice.mapper.AuthMaper;
import com.acsertia.authservice.repository.UsersRepository;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
