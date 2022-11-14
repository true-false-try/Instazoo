package com.example.instazoo.web.controller;

import com.example.instazoo.entity.ImageModel;
import com.example.instazoo.payload.response.MessageResponse;
import com.example.instazoo.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("api/image")
@CrossOrigin
@RequiredArgsConstructor
public class ImageUploadController {
    private final ImageUploadService imageUploadService;
    private final String SUCCESS_MESSAGE = "Image Upload Successfully";

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file")MultipartFile file,
                                                             Principal principal) throws IOException {
        imageUploadService.uploadImageToUser(file, principal);
        return ResponseEntity.ok(new MessageResponse(SUCCESS_MESSAGE));

    }

    @PostMapping("{postId}/upload")
    public ResponseEntity<MessageResponse> uploadImageToPost(@PathVariable("postId") Long postId,
                                                             @RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException{
    imageUploadService.uploadImageToPost(file, principal, postId);
    return ResponseEntity.ok(new MessageResponse(SUCCESS_MESSAGE));

    }

    @GetMapping("profileImage")
    public ResponseEntity<ImageModel> getImageForUser(Principal principal) {
        ImageModel userImage =  imageUploadService.getImageToUser(principal);
        return new ResponseEntity<>(userImage, HttpStatus.OK);

    }

    @GetMapping("{postId}/image")
    public ResponseEntity<ImageModel> getImageToPost(@PathVariable("postId") Long postId) {
        ImageModel postImage = imageUploadService.getImageToPost(postId);
        return new ResponseEntity<>(postImage, HttpStatus.OK);

    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String getIOException(IOException exception) {
        return exception.getMessage();
    }


}
