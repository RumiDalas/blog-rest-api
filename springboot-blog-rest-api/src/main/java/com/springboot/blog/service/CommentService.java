package com.springboot.blog.service;

import com.springboot.blog.payload.CoomentDto;

import java.util.List;

public interface CommentService {
    CoomentDto createComment (long postId , CoomentDto coomentDto);
    List<CoomentDto> getCommentsByPostId(long postId);

    CoomentDto getCommentById(Long postId , Long commentId);

    CoomentDto updateComment(Long postId , long commentId , CoomentDto commentRequest );

    void deleteComment(Long postId , long commentId );

}
