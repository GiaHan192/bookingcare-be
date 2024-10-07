package com.company.myweb.service;

import com.company.myweb.dto.PostDTO;
import com.company.myweb.entity.common.ApiPage;
import com.company.myweb.payload.request.CreatePostRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    ApiPage<PostDTO> getAllPost(String query, Pageable pageable);

    PostDTO getSinglePost(Long id);

    Boolean createPost(CreatePostRequest request);

    Boolean updatePost(Long id, CreatePostRequest request);

    Boolean deletePost(Long id);
}
