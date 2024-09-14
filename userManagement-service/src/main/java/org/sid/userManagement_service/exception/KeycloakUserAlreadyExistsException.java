package org.sid.userManagement_service.exception;

public class KeycloakUserAlreadyExistsException extends RuntimeException {
    public KeycloakUserAlreadyExistsException(String message) {
        super(message);
    }
}