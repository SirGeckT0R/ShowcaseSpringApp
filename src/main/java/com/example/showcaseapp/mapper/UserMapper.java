package com.example.showcaseapp.mapper;

import com.example.showcaseapp.dto.UserDto;
import com.example.showcaseapp.entity.User;

public class UserMapper {
    public static UserDto map(User user) {
        return new UserDto(user.getUserId(), user.getEmail(), user.getRole(),user.getUsername(),user.getPersonalRatings());
    }
}
