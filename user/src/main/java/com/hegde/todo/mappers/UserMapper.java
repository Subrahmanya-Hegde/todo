package com.hegde.todo.mappers;

import com.hegde.todo.domain.User;
import com.hegde.todo.dto.SignupDto;
import com.hegde.todo.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signupToUser(SignupDto signupDto);
}
