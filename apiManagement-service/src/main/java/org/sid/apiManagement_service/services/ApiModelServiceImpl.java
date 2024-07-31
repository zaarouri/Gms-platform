//package org.sid.apiManagement_service.services;
//
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.sid.apiManagement_service.dtos.ApiModelDto;
//import org.sid.apiManagement_service.enums.ApiType;
//import org.sid.apiManagement_service.mappers.ApiModelMapper;
//import org.sid.apiManagement_service.models.ApiModel;
//import org.sid.apiManagement_service.repositories.ApiModelRepo;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//
//public class ApiModelServiceImpl implements ApiModelService {
//
//    private final ApiModelRepo apiModelRepository;
//    private final ApiModelMapper apiModelMapper;
//
//
//
//    @Override
//    public ApiModelDto createApi(ApiModelDto apiModelDto) throws EntityNotFoundException {
//        ApiModel apiModel = apiModelMapper.toEntity(apiModelDto);
//        apiModel.setCreatedAt(new Date());
//        ApiModel savedModel = apiModelRepository.save(apiModel);
//        return apiModelMapper.toDto(savedModel);
//    }
//
//    @Override
//    @Transactional()
//    public ApiModelDto getApiById(String id)throws EntityNotFoundException {
//        ApiModel apiModel = apiModelRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("API not found with id: " + id));
//        return apiModelMapper.toDto(apiModel);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ApiModelDto> getAllApis() throws EntityNotFoundException {
//        List<ApiModel> apiModels = apiModelRepository.findAll();
//        return apiModels.stream()
//                .map(apiModelMapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public ApiModelDto updateApi(String id, ApiModelDto apiModelDto) throws EntityNotFoundException{
//        ApiModel existingApiModel = apiModelRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("API not found with id: " + id));
//
//        apiModelMapper.updateEntityFromDto(apiModelDto, existingApiModel);
//        ApiModel updatedModel = apiModelRepository.save(existingApiModel);
//        return apiModelMapper.toDto(updatedModel);
//    }
//
//    @Override
//    public void deleteApi(String id) throws EntityNotFoundException{
//        if (!apiModelRepository.existsById(id)) {
//            throw new EntityNotFoundException("API not found with id: " + id);
//        }
//        apiModelRepository.deleteById(id);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ApiModelDto> getApisByType(ApiType type) throws EntityNotFoundException {
//        List<ApiModel> apiModels = apiModelRepository.findByType(type);
//        return apiModels.stream()
//                .map(apiModelMapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ApiModelDto> getApisSupportingMethod(HttpMethod method) throws EntityNotFoundException {
//        List<ApiModel> apiModels = apiModelRepository.findBySupportedMethodsContaining(method);
//        return apiModels.stream()
//                .map(apiModelMapper::toDto)
//                .collect(Collectors.toList());
//    }
//}