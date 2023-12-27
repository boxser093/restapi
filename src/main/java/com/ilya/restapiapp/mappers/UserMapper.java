package com.ilya.restapiapp.mappers;

import com.ilya.restapiapp.dto.UserDto;
import com.ilya.restapiapp.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper implements GenericMapper<User, UserDto> {

    private EventMapper eventMapper = new EventMapper();
    @Override
    public UserDto map(User user) {

        if(user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .listEvents(eventMapper.map(user.getEvents()))
                .build();
    }

    @Override
    public List<UserDto> map(List<User> users) {
        if(users == null || users.size()==0) return null;
        return users.stream().map(this::map).collect(Collectors.toList());
    }
}
