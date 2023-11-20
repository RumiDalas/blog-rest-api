package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CoomentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository ;
    private final PostRepository postRepository ;
    private final ModelMapper mapper ;

    public CommentServiceImpl(CommentRepository commentRepository , PostRepository postRepository , ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository ;
        this.mapper = mapper;
    }

    @Override
    public CoomentDto createComment(long postId, CoomentDto coomentDto) {
        Comment comment = mapToEntity(coomentDto);
//        retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));

//        set post to comment entity
        comment.setPost(post);

//        comment entity to db
       Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CoomentDto> getCommentsByPostId(long postId) {
//        retrieve comment by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
//        convert list of comment entities to list of comment dtos

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CoomentDto getCommentById(Long postId, Long commentId) {
        //        retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));
//        retrieve comment by id

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment" , "id", commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CoomentDto updateComment(Long postId, long commentId, CoomentDto commentRequest) {
        //        retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));
//        retrieve comment by id

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment" , "id", commentId)
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Comment does not belong to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updateComment = commentRepository.save(comment);
        return mapToDto(updateComment);
    }

    @Override
    public void deleteComment(Long postId, long commentId) {
        //        retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));
//        retrieve comment by id

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment" , "id", commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST , "Comment does not belong to post");
        }

        commentRepository.delete(comment);

    }

    //    converted entity into dto
    private CoomentDto mapToDto(Comment comment){
        CoomentDto coomentDto = mapper.map(comment , CoomentDto.class);
//        CoomentDto coomentDto = new CoomentDto();
//        coomentDto.setId(comment.getId());
//        coomentDto.setName(comment.getName());
//        coomentDto.setEmail(comment.getEmail());
//        coomentDto.setBody(comment.getBody());
        return coomentDto ;

    }

    //    converted dto into entity

    private Comment mapToEntity(CoomentDto coomentDto){

        Comment comment = mapper.map(coomentDto , Comment.class);
//        Comment comment =new Comment();
//        comment.setId(coomentDto.getId());
//        comment.setName(coomentDto.getName());
//        comment.setEmail(coomentDto.getEmail());
//        comment.setBody(coomentDto.getBody());
        return comment ;

    }


}
