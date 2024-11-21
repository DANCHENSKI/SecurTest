package ru.danchenski.TaskManagementSystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.danchenski.TaskManagementSystem.entities.UserEntity;
import ru.danchenski.TaskManagementSystem.models.UserDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    List<UserDTO> toUsersDTOs(List<UserEntity> userEntities);

    List<UserEntity> toUsersEntities(List<UserDTO> userDTOs);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true), // Или укажите источник, если он есть в DTO
            @Mapping(target = "updatedAt", ignore = true), // Или укажите источник, если он есть в DTO
            @Mapping(target = "role", ignore = true)      // Или укажите источник, если он есть в DTO
    })
    UserEntity toUserEntity(UserDTO userDTO);

    UserDTO toUserDTO(UserEntity userEntity);
}
