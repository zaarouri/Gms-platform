//package org.sid.userManagement_service.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.sid.userManagement_service.dtos.UserDto;
//import org.sid.userManagement_service.services.UserService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/users")
//public class UserManagementController {
//    private final UserService userService;
//
//    @GetMapping("/all")
//    public List<UserDto> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/{id}")
//    public UserDto getUserByid(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }
//    @PostMapping("/save")
//    public UserDto createUser(@RequestBody UserDto userDto) {
//        return userService.createUser(userDto);
//    }
//    @PutMapping("/update")
//    public UserDto updateUser(@RequestBody UserDto userDto) {
//        return userService.updateUser(userDto);
//    }
//
//    @PostMapping("/assignUserToApi")
//    public UserDto assignUserToApi(@RequestParam String apiId, @RequestParam Long userId) {
//        UserDto userDto = userService.assignApiModeltoUser(apiId, userId);
//        return userDto;
//    }
//}
