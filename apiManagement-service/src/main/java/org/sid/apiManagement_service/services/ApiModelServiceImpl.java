package org.sid.apiManagement_service.services;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sid.apiManagement_service.dtos.ApiModelDto;
import org.sid.apiManagement_service.mappers.ApiModelMapper;
import org.sid.apiManagement_service.models.ApiModel;
import org.sid.apiManagement_service.repositories.ApiModelRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiModelServiceImpl implements ApiModelService {

    private final ApiModelMapper mapper;

    private final ApiModelRepo repo;


    @Override
    public ApiModelDto addApiModel(ApiModelDto apiModelDto) {
        ApiModel entity = mapper.toEntity(apiModelDto);
        entity.setId(UUID.randomUUID().toString());
        entity.setCreatedAt(new Date());
        ApiModel savedEntity = repo.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public ApiModelDto updateApiModel(ApiModelDto apiModelDto) {
        if (!repo.existsById(apiModelDto.getId())) {
            throw new EntityNotFoundException("ApiModel with ID " + apiModelDto.getId() + " not found");
        }
        ApiModel entity = mapper.toEntity(apiModelDto);
        return mapper.toDto(repo.save(entity));

    }

    @Override
    public ApiModelDto getApiModelById(String id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("ApiModel with ID " + id + " not found"));

    }

    @Override
    public List<ApiModelDto> getAllApiModels() throws EntityNotFoundException {
        List<ApiModelDto> collect ;
        try {
            collect= repo.findAll().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
        return collect;
    }

    @Override
    public void deleteApiModelById(String id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("ApiModel with ID " + id + " not found");
        }
        repo.deleteById(id);

    }

    @Override
    public ApiModelDto getApiModelByName(String name) throws EntityNotFoundException {
        ApiModel apiModel = repo.findByName(name);
        if (apiModel == null) {
           throw new EntityNotFoundException("ApiModel with name " + name + " not found");
        }
        return mapper.toDto(apiModel);
    }

}
