package ru.itis.orisjavaproject.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.itis.orisjavaproject.Entities.Comment;
import ru.itis.orisjavaproject.dto.CommentDto;

@Mapper(componentModel = "spring")
@Component
public interface CommentMapper {

    @Mapping(source = "user.firstName", target = "name")
    CommentDto commentToCommentDtoWithUserName(Comment comment);
}