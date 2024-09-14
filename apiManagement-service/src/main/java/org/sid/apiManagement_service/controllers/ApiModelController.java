package org.sid.apiManagement_service.controllers;

import lombok.RequiredArgsConstructor;
import org.sid.apiManagement_service.dtos.ApiModelDto;
import org.sid.apiManagement_service.enums.ApiType;
import org.sid.apiManagement_service.enums.HttpMethod;
import org.sid.apiManagement_service.services.ApiModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/apis")
@RequiredArgsConstructor
public class ApiModelController {
    private final ApiModelService apiModelService;

    @PostMapping("/apis-save")
    public ResponseEntity<ApiModelDto> createApi(@RequestBody ApiModelDto apiModelDto) {
        ApiModelDto createdApi = apiModelService.createApi(apiModelDto);
        return new ResponseEntity<>(createdApi, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiModelDto> getApiById(@PathVariable String id) {
        ApiModelDto api = apiModelService.getApiById(id);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/apis-all")
    public ResponseEntity<List<ApiModelDto>> getAllApis() {
        List<ApiModelDto> apis = apiModelService.getAllApis();
        return ResponseEntity.ok(apis);
    }

    @PutMapping("/apis-update/{id}")
    public ResponseEntity<ApiModelDto> updateApi(@PathVariable String id, @RequestBody ApiModelDto apiModelDto) {
        ApiModelDto updatedApi = apiModelService.updateApi(id, apiModelDto);
        return ResponseEntity.ok(updatedApi);
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<Void> archiveApi(@PathVariable String id) {
        apiModelService.archiveApiModel(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/unarchive/{id}")
    public ResponseEntity<Void> unarchiveApi(@PathVariable String id) {
        apiModelService.unarchiveApiModel(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ApiModelDto>> getApisByType(@PathVariable ApiType type) {
        List<ApiModelDto> apis = apiModelService.getApisByType(type);
        return ResponseEntity.ok(apis);
    }

    @GetMapping("/method/{method}")
    public ResponseEntity<List<ApiModelDto>> getApisSupportingMethod(@PathVariable HttpMethod method) {
        List<ApiModelDto> apis = apiModelService.getApisSupportingMethod(method);
        return ResponseEntity.ok(apis);
    }

    @GetMapping("/archived")
    public List<ApiModelDto> getAllArchivedApiModels() {
        return apiModelService.getAllArchivedApis();
    }

    @GetMapping("/apis")
    public ResponseEntity<List<ApiModelDto>> getAll() {
        List<ApiModelDto> apis = apiModelService.getAll();
        return ResponseEntity.ok(apis);
    }
}