package com.example.instazoo.mapping;

import com.example.instazoo.dto.CommentDTO;
import com.example.instazoo.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    CommentDTO commentToCommentDTO(Comment comment);

}
