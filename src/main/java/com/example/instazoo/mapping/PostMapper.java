package com.example.instazoo.mapping;

import com.example.instazoo.dto.PostDTO;
import com.example.instazoo.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    PostDTO postToPostDTO(Post post);
}
