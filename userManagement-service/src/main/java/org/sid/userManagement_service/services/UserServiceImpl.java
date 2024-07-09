package org.sid.userManagement_service.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sid.userManagement_service.clients.ApiModelRestClient;
import org.sid.userManagement_service.dtos.UserDto;
import org.sid.userManagement_service.entities.UserModel;
import org.sid.userManagement_service.mappers.UserMapper;
import org.sid.userManagement_service.models.ApiModel;
import org.sid.userManagement_service.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final ApiModelRestClient apiModelRestClient;
    private final UserMapper mapper;

    @Override
    public List<UserDto> getAllUsers() {
        List<UserModel> userModels = userRepository.findAll();
        return userModels.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(long id) {
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toDto(userModel);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserModel userModel = mapper.toEntity(userDto);
        UserModel saved = userRepository.save(userModel);
        return mapper.toDto(saved);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (!userRepository.existsById(userDto.getId())) {
            throw new RuntimeException("User not found");
        }
        UserModel userModel = mapper.toEntity(userDto);
        UserModel saved = userRepository.save(userModel);
        return mapper.toDto(saved);
    }

    @Override
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserModel userModel = userRepository.findByUsername(username);
        return mapper.toDto(userModel);
    }

    @Override
    public UserDto assignApiModeltoUser(String apiId, Long userId) {
        // Fetch the ApiModel using the Feign client
        ApiModel apiModel = apiModelRestClient.getById(apiId);
        if (apiModel == null) {
            throw new RuntimeException("ApiModel not found");
        }

        // Fetch the user from the repository
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Add the new ApiModel ID to the existing list of IDs
        List<String> apiModelsIds = Optional.ofNullable(userModel.getApiModelsIds()).orElse(new ArrayList<>());
        if (!apiModelsIds.contains(apiId)) {
            apiModelsIds.add(apiId);
        }
        userModel.setApiModelsIds(apiModelsIds);

        // Save the updated user model to the repository
        userRepository.save(userModel);

        // Map the user model to a DTO and include the full ApiModel details
        UserDto userDto = mapper.toDto(userModel);
        List<ApiModel> apiModels = apiModelsIds.stream()
                .map(apiModelRestClient::getById)
                .collect(Collectors.toList());
        userDto.setApiModels(apiModels);

        return userDto;
    }
}
