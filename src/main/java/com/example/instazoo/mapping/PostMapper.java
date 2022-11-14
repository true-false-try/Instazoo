package com.example.instazoo.mapping;

import com.example.instazoo.dto.PostDTO;
import com.example.instazoo.entity.Post;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PostMapper {
    PostDTO postToPostDTO(Post post);
}
