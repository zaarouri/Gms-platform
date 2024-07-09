package org.sid.apiManagement_service.controllers;
import lombok.RequiredArgsConstructor;
import org.sid.apiManagement_service.dtos.ApiModelDto;
import org.sid.apiManagement_service.services.ApiModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/apis")
@RequiredArgsConstructor
public class ApiModelController {

    private final ApiModelService service;

    @GetMapping
    public List<ApiModelDto> getAll() {
        return service.getAllApiModels();
    }

    @GetMapping("/{id}")
    public ApiModelDto getById(@PathVariable String id) {

        return service.getApiModelById(id);
    }

    @PostMapping("/save")
    public ApiModelDto create(@RequestBody ApiModelDto dto) {

        return service.addApiModel(dto);
    }

    @PutMapping("/{id}")
    public ApiModelDto update(@PathVariable String id, @RequestBody ApiModelDto dto) {
        dto.setId(id);
        return service.updateApiModel(dto);
    }

    @DeleteMapping("/{id}")
    public ApiModelDto delete(@PathVariable String id) {
        ApiModelDto deletedDto = service.getApiModelById(id);
        service.deleteApiModelById(id);
        return deletedDto;
    }
//    @GetMapping("/api/{apiId}")
//    UserModel getUserByApiId(@PathVariable String apiId)
}
