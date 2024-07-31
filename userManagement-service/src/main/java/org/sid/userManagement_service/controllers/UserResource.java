package org.sid.userManagement_service.controllers;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.sid.userManagement_service.dtos.UserDto;
import org.sid.userManagement_service.models.NewUserRecord;
import org.sid.userManagement_service.services.KeycloakClientService;
import org.sid.userManagement_service.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {
    private final KeycloakClientService keycloakClientService;

//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) throws Exception {
//      keycloakClientService.createUser(userDto);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
@PostMapping
public ResponseEntity<?> createUser(@RequestBody NewUserRecord newUserRecord) {

    keycloakClientService.createUser(newUserRecord);
    return ResponseEntity.status(HttpStatus.CREATED).build();
}
@GetMapping
    public String test (){
    return "tested ";
}


}
