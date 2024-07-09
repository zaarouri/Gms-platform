package org.sid.userManagement_service.repositories;

import org.sid.userManagement_service.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
