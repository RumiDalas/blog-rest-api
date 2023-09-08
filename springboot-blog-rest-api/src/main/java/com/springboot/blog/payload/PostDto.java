package com.springboot.blog.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
//    tiitle should not be null or empty
//    title should have at least two character
    @NotEmpty
    @Size(min=2 , message = "Post title should have at least two character. ")
    private String title;

    @NotEmpty
    @Size(min=10 , message = "Post title should have at least two character. ")
    private String description;

    @NotEmpty
    private String content ;
    private Set<CoomentDto> comments ;
}
