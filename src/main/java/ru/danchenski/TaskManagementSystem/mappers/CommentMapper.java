package ru.danchenski.TaskManagementSystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.danchenski.TaskManagementSystem.entities.CommentEntity;
import ru.danchenski.TaskManagementSystem.models.CommentDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    List<CommentDTO> toCommentsDTOs(List<CommentEntity> commentEntities);

    List<CommentEntity> toCommentsEntities(List<CommentDTO> commentDTOs);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    CommentEntity toCommentEntity(CommentDTO commentDTO);

    CommentDTO toCommentDTO(CommentEntity commentEntity);
}
