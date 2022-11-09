package com.example.instazoo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class CommentDTO {
    private Long id;
    @NotEmpty
    private String message;
    @NotEmpty
    private String username;

}
