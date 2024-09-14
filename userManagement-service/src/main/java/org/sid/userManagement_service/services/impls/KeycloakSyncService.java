package org.sid.userManagement_service.services.impls;

import org.keycloak.admin.client.Keycloak;


import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.sid.userManagement_service.entities.UserModel;
import org.sid.userManagement_service.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KeycloakSyncService {

    private final UserRepo userRepository;
    private final Keycloak keycloak;
    private final String realm;

    @Autowired
    public KeycloakSyncService(UserRepo userRepository, Keycloak keycloak, @Value("${app.keycloak.realm}") String realm) {
        this.userRepository = userRepository;
        this.keycloak = keycloak;
        this.realm = realm;
    }


    @Scheduled(fixedRate = 1000) // Synchronisation toutes les heures
    public void syncUsers() {
        // Récupérer tous les utilisateurs de Keycloak
        List<UserRepresentation> keycloakUsers = keycloak.realm(realm).users().list();
        Set<String> keycloakUserIds = keycloakUsers.stream()
                .map(UserRepresentation::getId)
                .collect(Collectors.toSet());

        // Récupérer tous les utilisateurs de la base de données
        List<UserModel> allUsersInDb = userRepository.findAll();

        for (UserModel userInDb : allUsersInDb) {
            // Si l'utilisateur en base de données n'existe plus dans Keycloak, le supprimer
            if (!keycloakUserIds.contains(userInDb.getKeycloakId())) {
                deleteUserFromDatabase(userInDb.getKeycloakId());
            }
        }

        // Synchroniser ou créer les utilisateurs restants
        for (UserRepresentation keycloakUser : keycloakUsers) {
            syncUser(keycloakUser);
        }
    }
    private void syncUser(UserRepresentation keycloakUser) {
        Optional<UserModel> user = userRepository.findByKeycloakId(keycloakUser.getId());
        if (user.isPresent()) {
            updateUserInDatabase(user.get(), keycloakUser);
        } else {
            createUserInDatabase(keycloakUser);
        }
    }
    @CacheEvict(value = "users", allEntries = true)
    public void deleteUserFromDatabase(String keycloakUserId) {
        // Log the incoming Keycloak ID for debugging
        System.out.println("Attempting to delete user with Keycloak ID: " + keycloakUserId);

        Optional<UserModel> userOptional = userRepository.findByKeycloakId(keycloakUserId);

        if (userOptional.isPresent()) {
            // Log that the user was found
            System.out.println("User found. Deleting user with Keycloak ID: " + keycloakUserId);
            userRepository.delete(userOptional.get());
        } else {
            // Log that the user was not found and throw an exception
            System.out.println("User with Keycloak ID " + keycloakUserId + " not found.");
            throw new RuntimeException("User with Keycloak ID " + keycloakUserId + " not found.");
        }
    }

    @CacheEvict(value = "users", allEntries = true)
    public void updateUserInDatabase(UserModel userModel, UserRepresentation keycloakUser) {
        userModel.setUsername(keycloakUser.getUsername());
        userModel.setEmail(keycloakUser.getEmail());

        List<String> keycloakRoles = keycloak.realm(realm)
                .users()
                .get(keycloakUser.getId())
                .roles()
                .realmLevel()
                .listEffective()
                .stream()
                .map(RoleRepresentation::getName)
                .filter(role -> role.equals("ADMIN") || role.equals("USER"))
                .collect(Collectors.toList());

        userModel.setRoles(keycloakRoles);
        userRepository.save(userModel);
    }

    private void createUserInDatabase(UserRepresentation keycloakUser) {
        UserModel newUser = new UserModel();
        newUser.setKeycloakId(keycloakUser.getId());
        newUser.setUsername(keycloakUser.getUsername());
        newUser.setEmail(keycloakUser.getEmail());

        List<String> roles = keycloak.realm(realm)
                .users()
                .get(keycloakUser.getId())
                .roles()
                .realmLevel()
                .listEffective()
                .stream()
                .map(RoleRepresentation::getName)
                .filter(role -> role.equals("ADMIN") || role.equals("USER"))
                .collect(Collectors.toList());

        newUser.setRoles(roles);

        userRepository.save(newUser);
    }

}