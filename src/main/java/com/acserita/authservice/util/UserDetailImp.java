package com.acserita.authservice.util;

import com.acserita.authservice.entities.Users;
import com.acserita.authservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class UserDetailImp implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users=usersRepository.findByUsername(username);
        if(users.isPresent()) {
            return new User(username, users.get().getPassword(),
                    new ArrayList<>());
        }
        else
        {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
