//package org.sid.userManagement_service.controllers;
//
//import org.keycloak.representations.idm.UserRepresentation;
//import org.sid.userManagement_service.entities.UserModel;
//import org.sid.userManagement_service.services.KeycloakClientService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//    @Autowired
//    private KeycloakClientService keycloakClientService;
//
//    // Create a new user with roles
//    @PostMapping
//    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
//        UserModel createdUser = keycloakClientService.createUser(userModel);
//        return ResponseEntity.ok(createdUser);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<UserRepresentation>> getAllUsers() {
//        List<UserRepresentation> users = keycloakClientService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserRepresentation> getUserById(@PathVariable String id) {
//        UserRepresentation user = keycloakClientService.getUserById(id);
//        return ResponseEntity.ok(user);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRepresentation user) {
//        keycloakClientService.updateUser(id, user);
//        return ResponseEntity.ok("User updated successfully");
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable String id) {
//        keycloakClientService.deleteUser(id);
//        return ResponseEntity.ok("User deleted successfully");
//    }
//}