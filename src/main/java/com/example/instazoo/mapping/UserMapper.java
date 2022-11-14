package com.example.instazoo.mapping;

import com.example.instazoo.dto.UserDTO;
import com.example.instazoo.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    @Mappings({
            @Mapping(target = "firstname", source = "name")
    })
    UserDTO userToUserDTO(User user);
}
