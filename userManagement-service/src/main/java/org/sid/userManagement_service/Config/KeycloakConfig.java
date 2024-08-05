package org.sid.userManagement_service.Config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class KeycloakConfig {

    @Value("${app.keycloak.admin.clientId}")
    private String clientId;
    @Value("${app.keycloak.admin.clientSecret}")
    private String clientSecret;
    @Value("${app.keycloak.realm}")
    private String realm;
    @Value("${app.keycloak.serverUrl}")
    private String serverUrl;

    @Bean
    public Keycloak keycloak() {
        try {
            return KeycloakBuilder.builder()
                    .clientSecret(clientSecret)
                    .clientId(clientId)
                    .grantType("client_credentials")
                    .realm(realm)
                    .serverUrl(serverUrl)
                    .build();
        } catch (Exception e) {
            log.error("Error creating Keycloak client: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to configure Keycloak client", e);
        }
    }
}
