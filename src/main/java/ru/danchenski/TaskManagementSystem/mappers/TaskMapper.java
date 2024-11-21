package ru.danchenski.TaskManagementSystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.danchenski.TaskManagementSystem.entities.TaskEntity;
import ru.danchenski.TaskManagementSystem.entities.UserEntity;
import ru.danchenski.TaskManagementSystem.models.TaskDTO;
import ru.danchenski.TaskManagementSystem.models.UserDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    List<TaskDTO> toTasksDTOs(List<TaskEntity> taskEntities);

    List<TaskEntity> toTasksEntities(List<TaskDTO> taskDTOs);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(source = "author", target = "author", qualifiedByName = "userDtoToUserEntity"),
            @Mapping(source = "executor", target = "executor", qualifiedByName = "userDtoToUserEntity")

    })
    TaskEntity toTaskEntity(TaskDTO taskDTO);

    TaskDTO toTaskDTO(TaskEntity taskEntity);

    @Named("userDtoToUserEntity") // Аннотация @Named
    UserEntity userDtoToUserEntity(UserDTO userDTO);
}
