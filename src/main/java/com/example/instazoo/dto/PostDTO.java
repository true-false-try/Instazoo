package com.example.instazoo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String caption;
    private String location;
    private String username;
    private String likes;
    private Set<String> userLiked;

}
