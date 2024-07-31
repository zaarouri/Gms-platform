package org.sid.userManagement_service.services;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.sid.userManagement_service.config.KeycloakConfig;
import org.sid.userManagement_service.dtos.UserDto;
import org.sid.userManagement_service.entities.UserModel;
import org.sid.userManagement_service.mappers.UserMapper;
import org.sid.userManagement_service.models.NewUserRecord;
import org.sid.userManagement_service.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakClientService {

    @Value("${app.keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

//    public void createUser(UserDto userDto) throws Exception {
//        // Create UserRepresentation for Keycloak
//        UserRepresentation userRepresentation = new UserRepresentation();
//        userRepresentation.setEnabled(true);
//        userRepresentation.setFirstName(userDto.getFirstName());
//        userRepresentation.setLastName(userDto.getLastName());
//        userRepresentation.setUsername(userDto.getUsername());
//        userRepresentation.setEmail(userDto.getEmail());
//        userRepresentation.setEmailVerified(false);
//
//
//        // Set password
//
//        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
//        credentialRepresentation.setValue(userDto.getPassword());
//        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
//
//        userRepresentation.setCredentials(List.of(credentialRepresentation));
//
//        // Create user in Keycloak
//        UsersResource usersResource = getUsersResource();
//
//        Response response = usersResource.create(userRepresentation);
//
//        log.info("Status Code " + response.getStatus());
//
//        if (!Objects.equals(201, response.getStatus())) {
//
//            throw new RuntimeException("Status code " + response.getStatus());
//        }
//        log.info("New user has bee created");
//
//
//    }

// public List<UserRepresentation> getAllUsers() {
//   return keycloak.realm("your-realm-name").users().list();
// }
//
//    public UserRepresentation getUserById(String userId) {
//        return null;
//    }
//
//    public void updateUser(String userId, UserRepresentation user) {
//    }
//
//    public void deleteUser(String userId) {
//    }
//
//
//    public boolean canUserAccessApi(Long userId, String apiId) {
//        UserModel user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        return user.getApiModelsIds().contains(apiId);
//    }

    public void createUser(NewUserRecord newUserRecord) {

        UserRepresentation  userRepresentation= new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(newUserRecord.firstName());
        userRepresentation.setLastName(newUserRecord.lastName());
        userRepresentation.setUsername(newUserRecord.username());
        userRepresentation.setEmail(newUserRecord.username());
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(newUserRecord.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(List.of(credentialRepresentation));

        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(userRepresentation);

        log.info("Status Code "+response.getStatus());

        if(!Objects.equals(201,response.getStatus())){
            throw new RuntimeException("Status code "+response.getStatus());
        }

        log.info("New user has bee created");

        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(newUserRecord.username(), true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
    }
}
