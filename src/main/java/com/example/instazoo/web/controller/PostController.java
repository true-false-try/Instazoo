package com.example.instazoo.web.controller;

import com.example.instazoo.dto.PostDTO;
import com.example.instazoo.entity.Post;
import com.example.instazoo.mapping.PostMapper;
import com.example.instazoo.payload.response.MessageResponse;
import com.example.instazoo.service.PostService;
import com.example.instazoo.validations.ResponseErrorValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/post")
@CrossOrigin
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ResponseErrorValidation responseErrorValidation;

    @PostMapping("create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO,
                                             BindingResult result,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Post post = postService.createPost(postDTO, principal);
        PostDTO createPost = PostMapper.INSTANCE.postToPostDTO(post);

        return new ResponseEntity<>(createPost, HttpStatus.OK);

    }

    @GetMapping("all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postDTOS = postService.getAllPosts()
                .stream()
                .map(PostMapper.INSTANCE::postToPostDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    @GetMapping("user/post")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(Principal principal) {
        List<PostDTO> postDTOS = postService.getAllPostsForUser(principal)
                .stream()
                .map(PostMapper.INSTANCE::postToPostDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOS, HttpStatus.OK);

    }

    @PostMapping("{postId}/{username}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable("postId") Long postId,
                                            @PathVariable("username") String username) {
        Post post = postService.likePost(postId, username);
        PostDTO postDTO = PostMapper.INSTANCE.postToPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);

    }

    @DeleteMapping("{postId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") Long postId, Principal principal) {
        postService.deletePost(postId, principal);
        return new ResponseEntity<>(new MessageResponse("Post was deleted"), HttpStatus.OK);
    }


}
