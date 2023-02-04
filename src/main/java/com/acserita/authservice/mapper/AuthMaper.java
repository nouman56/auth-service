package com.acserita.authservice.mapper;

import com.acserita.authservice.dto.UsersDTO;
import com.acserita.authservice.entities.Users;
import org.springframework.stereotype.Component;

@Component
public class AuthMaper {

    public Users DtoToEntity(UsersDTO usersDTO){
        return Users.builder().username(usersDTO.getUsername()).password(usersDTO.getPassword()).build();
    }
    public UsersDTO EntityToDto(Users user){
        return UsersDTO.builder().username(user.getUsername()).password(user.getPassword()).build();
    }
}
