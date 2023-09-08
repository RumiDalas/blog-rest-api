package com.springboot.blog.controller;

import com.springboot.blog.payload.CoomentDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")

public class CommentController {
    private CommentService commentService ;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CoomentDto> createComment (@PathVariable(value ="postId") Long postId ,
                                                @Valid @RequestBody CoomentDto coomentDto){

        return new ResponseEntity<>(commentService.createComment(postId , coomentDto), HttpStatus.CREATED);

    }

    @GetMapping("/posts/{postId}/comments")
    public List<CoomentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);

    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public  ResponseEntity<CoomentDto> getCommentById(@PathVariable(value = "postId") Long postId ,
                                                      @PathVariable(value = "id") Long commentId){
        CoomentDto coomentDto = commentService.getCommentById(postId , commentId);
        return new ResponseEntity<>(coomentDto , HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public  ResponseEntity<CoomentDto> updateComment(@PathVariable(value = "postId") Long postId ,
                                                      @PathVariable(value = "id") Long commentId,
                                                     @Valid  @RequestBody CoomentDto coomentDto){
        CoomentDto updateComment = commentService.updateComment(postId , commentId , coomentDto );
        return new ResponseEntity<>(updateComment , HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public  ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId ,
                                                     @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId , commentId );
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }



}
