package org.sid.userManagement_service.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.sid.userManagement_service.dtos.AuthResponse;
import org.sid.userManagement_service.dtos.UserDto;
import org.sid.userManagement_service.entities.UserModel;
import org.sid.userManagement_service.exception.AuthenticationException;
import org.sid.userManagement_service.exception.InvalidCredentialsException;
import org.sid.userManagement_service.exception.KeycloakUnavailableException;
import org.sid.userManagement_service.exception.UserNotFoundException;
import org.sid.userManagement_service.mappers.UserMapper;
import org.sid.userManagement_service.repositories.UserRepo;
import org.sid.userManagement_service.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepository;
    private final Keycloak keycloak;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.keycloak.realm}")
    private String realm;
    @Value("${app.keycloak.admin.clientId}")
    private String clientId;
    @Value("${app.keycloak.admin.clientSecret}")
    private String clientSecret;
    @Value("${app.keycloak.serverUrl}")
    private String serverUrl;

    @Override
    public AuthResponse login(String username, String password)  throws KeycloakUnavailableException, InvalidCredentialsException {
        AuthResponse authResponse = loginWithKeycloak(username, password);
        return authResponse;
    }

    private AuthResponse loginWithKeycloak(String username, String password) {
        try {
            System.out.println("***************************************************************");
            System.out.println("loginWithKeycloak username: " + username);
            System.out.println("loginWithKeycloak password: " + password);
            log.info("Attempting Keycloak login for user: {}", username);
            AccessTokenResponse tokenResponse = keycloak.tokenManager().grantToken();
            if (tokenResponse == null || tokenResponse.getToken() == null) {
                throw new AuthenticationException("Failed to obtain access token from Keycloak");
            }

           // UserModel user = userRepository.findByUsername(username)
          //          .orElseThrow(() -> new UserNotFoundException("User not found in local database"));
          //  UserDto userDto = mapper.toDto(user);
            log.info( "this is the token "+tokenResponse.getToken());
            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(tokenResponse.getToken());
            return  authResponse;
        } catch (Exception e) {
            log.error("Keycloak login failed for user: {}", username, e);
            throw new KeycloakUnavailableException("Keycloak authentication failed", e);
        }
    }


    private boolean verifyPassword(String inputPassword, String storedHash) {
        return passwordEncoder.matches(inputPassword, storedHash);
    }

    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void logout(String token) {
        try {
            keycloak.tokenManager().invalidate(token);
            log.info("Successfully logged out user");
        } catch (Exception e) {
            log.error("Error during logout", e);
            throw new AuthenticationException("Failed to logout", e);
        }
    }
}