package org.sid.apiManagement_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.apiManagement_service.enums.Method;

import java.util.*;

@Entity
@Table(name = "apis")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiModel {
    @Id
    private String id;
    private String name ;
    private String url;
    private String type; // SOAP or REST
    private String username ;
    private String password ;
    private Date createdAt ;
    @Enumerated(EnumType.STRING)
    private Method method; // GET, POST, PUT, DELETE (for REST APIs)
    @ElementCollection
    @CollectionTable(name = "ws_headers", joinColumns = @JoinColumn(name = "ws_id"))
    @MapKeyColumn(name = "header_key")
    @Column(name = "header_value")
    private Map<String, String> headers = new HashMap<>();

}
