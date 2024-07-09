package org.sid.userManagement_service.services;

import org.sid.userManagement_service.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(long id);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    void deleteUser(long id);
    UserDto getUserByUsername(String username);
    UserDto assignApiModeltoUser(String apiId, Long userId);
}
