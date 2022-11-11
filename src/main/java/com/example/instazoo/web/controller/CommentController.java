package com.example.instazoo.web.controller;

import com.example.instazoo.dto.CommentDTO;
import com.example.instazoo.entity.Comment;
import com.example.instazoo.mapping.CommentMapper;
import com.example.instazoo.payload.response.MessageResponse;
import com.example.instazoo.service.CommentService;
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
@RequestMapping("api/comment")
@CrossOrigin
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ResponseErrorValidation responseErrorValidation;

    @PostMapping("{postId}/create")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDTO commentDTO,
                                                @PathVariable("postId") Long postId,
                                                BindingResult result,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Comment comment = commentService.saveComment(postId, commentDTO, principal);
        CommentDTO createComment = CommentMapper.INSTANCE.commentToCommentDTO(comment);

        return new ResponseEntity<>(createComment, HttpStatus.OK);
    }

    @GetMapping("{postId}/all")
    public ResponseEntity<List<CommentDTO>> getAllCommentsToPost(@PathVariable("postId") Long postId) {
        List<CommentDTO> commentDTOS = commentService.getAllCommentsForPost(postId)
                .stream()
                .map(CommentMapper.INSTANCE::commentToCommentDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);

    }

    @PostMapping("{commentId}/delete")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity<>(new MessageResponse("Comment was deleted"), HttpStatus.OK);

    }
}
