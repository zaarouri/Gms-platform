package org.sid.apiManagement_service.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.apiManagement_service.dtos.ApiModelDto;
import org.sid.apiManagement_service.enums.ApiType;
import org.sid.apiManagement_service.enums.HttpMethod;
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
@Slf4j
public class ApiModelServiceImp implements ApiModelService {

    private final ApiModelRepo apiModelRepository;
    private final ApiModelMapper apiModelMapper;

    /**
     * Create a new API model.
     *
     * @param apiModelDto The API model DTO to be created.
     * @return The created API model DTO.
     */
    @Override
    public ApiModelDto createApi(ApiModelDto apiModelDto) throws EntityExistsException {
        if (apiModelRepository.existsByName(apiModelDto.getName())) {
            throw new EntityExistsException("API model with name '" + apiModelDto.getName() + "' already exists.");
        }
        ApiModel apiModel = apiModelMapper.toEntity(apiModelDto);
        apiModel.setCreatedAt(new Date());
        apiModel.setId(UUID.randomUUID().toString());
        apiModel.setParameters(apiModelMapper.dtoListToParameterList(apiModelDto.getParameters()));
        ApiModel savedModel = apiModelRepository.save(apiModel);
        return apiModelMapper.toDto(savedModel);
    }


    /**
     * Get an API model by its ID.
     *
     * @param id The ID of the API model to retrieve.
     * @return The API model DTO.
     * @throws EntityNotFoundException if the API model is not found.
     */
    @Override
    public ApiModelDto getApiById(String id) throws EntityNotFoundException {
        ApiModel apiModel = apiModelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("API not found with id: " + id));
        if (apiModel.isArchived()) {
            log.debug("Archived api model");
        }
        return apiModelMapper.toDto(apiModel);
    }

    /**
     * Get all non-archived API models.
     *
     * @return A list of API model DTOs.
     * @throws EntityNotFoundException if no API models are found.
     */
    @Override
    public List<ApiModelDto> getAllApis() throws EntityNotFoundException {
        List<ApiModel> apiModels = apiModelRepository.findByArchivedFalse();
        return apiModels.stream()
                .map(apiModelMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing API model.
     *
     * @param id           The ID of the API model to update.
     * @param apiModelDto  The updated API model DTO.
     * @return The updated API model DTO.
     * @throws EntityNotFoundException if the API model is not found.
     */
    @Override
    public ApiModelDto updateApi(String id, ApiModelDto apiModelDto) throws EntityNotFoundException {
        if (!apiModelRepository.existsById(id)) {
            throw new EntityNotFoundException("API not found with id: " + id);
        }
        ApiModel apiModel = apiModelMapper.toEntity(apiModelDto);
        apiModel.setId(id);
        ApiModel updatedModel = apiModelRepository.save(apiModel);
        return apiModelMapper.toDto(updatedModel);
    }

    /**
     * Archive an API model.
     *
     * @param apiId The ID of the API model to archive.
     * @return The archived API model DTO.
     */
    @Override
    public ApiModelDto archiveApiModel(String apiId) {
        ApiModel apiModel = apiModelRepository.getById(apiId);
        if (apiModel == null) {
            throw new RuntimeException("ApiModel not found");
        }

        if (apiModel.isArchived()) {
            return apiModelMapper.toDto(apiModel);
        }
        ApiModel archivedApiModel = new ApiModel();
        archivedApiModel.setId(apiModel.getId());
        archivedApiModel.setName(apiModel.getName());
        archivedApiModel.setArchived(true);

        ApiModel updatedApiModel = apiModelRepository.save(archivedApiModel);
        return apiModelMapper.toDto(updatedApiModel);
    }

    /**
     * Get all API models of a specific type.
     *
     * @param type The type of API models to retrieve.
     * @return A list of API model DTOs.
     * @throws EntityNotFoundException if no API models are found.
     */
    @Override
    public List<ApiModelDto> getApisByType(ApiType type) throws EntityNotFoundException {
        List<ApiModel> apiModels = apiModelRepository.findByType(type);
        return apiModels.stream()
                .map(apiModelMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all API models that support a specific HTTP method.
     *
     * @param method The HTTP method to search for.
     * @return A list of API model DTOs.
     * @throws EntityNotFoundException if no API models are found.
     */
    @Override
    public List<ApiModelDto> getApisSupportingMethod(HttpMethod method) throws EntityNotFoundException {
        List<ApiModel> apiModels = apiModelRepository.findBySupportedMethodsContaining(method);
        return apiModels.stream()
                .map(apiModelMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all archived API models.
     *
     * @return A list of archived API model DTOs.
     * @throws EntityNotFoundException if no archived API models are found.
     */
    public List<ApiModelDto> getAllArchivedApis() throws EntityNotFoundException {
        List<ApiModel> archivedApis = apiModelRepository.findByArchivedTrue();
        if (archivedApis.isEmpty()) {
            throw new EntityNotFoundException("No archived API models found.");
        }
        return archivedApis.stream()
                .map(apiModelMapper::toDto)
                .collect(Collectors.toList());
    }

}