package org.sid.userManagement_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.userManagement_service.models.ApiModel;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String password;
    private String role;

    @ElementCollection
    @CollectionTable(name = "user_api", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "api_id")
    private List<String> apiModelsIds = new ArrayList<>();
    @Transient
    private List<ApiModel> apiModels = new ArrayList<>();
}