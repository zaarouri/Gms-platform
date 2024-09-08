package org.sid.apiconsumption_service.clients;

import org.sid.apiconsumption_service.models.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userManagement-service", url = "http://localhost:8081")
public interface UserClient {
    @GetMapping("/users/{keycloakId}")
    UserModel getUserById(@PathVariable String keycloakId);

}
