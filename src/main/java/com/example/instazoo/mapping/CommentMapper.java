package com.example.instazoo.mapping;

import com.example.instazoo.dto.CommentDTO;
import com.example.instazoo.entity.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {
    CommentDTO commentToCommentDTO(Comment comment);

}
