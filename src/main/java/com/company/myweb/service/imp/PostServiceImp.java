package com.company.myweb.service.imp;

import com.company.myweb.dto.PostDTO;
import com.company.myweb.entity.Post;
import com.company.myweb.entity.common.ApiException;
import com.company.myweb.entity.common.ApiPage;
import com.company.myweb.payload.request.CreatePostRequest;
import com.company.myweb.repository.PostRepository;
import com.company.myweb.service.PostService;
import com.company.myweb.utils.ObjectUtil;
import com.company.myweb.utils.PageUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class PostServiceImp implements PostService {
    private static final Logger log = LoggerFactory.getLogger(PostServiceImp.class);
    private final PostRepository postRepository;

    public PostServiceImp(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public ApiPage<PostDTO> getAllPost(String query, Pageable pageable) {
        Page<Post> posts = null;
        if (query != null && !query.trim().isEmpty()) {
            posts = postRepository.findAllByTitleContainingIgnoreCase(query, pageable);
        } else {
            posts = postRepository.findAll(pageable);
        }
        return PageUtil.convert(posts.map(this::convertEntityToDTO));
    }

    @Override
    public PostDTO getSinglePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Không tìm thấy bài viết với id:" + id));
        post.setReadNumber(post.getReadNumber() + 1);
        return ObjectUtil.copyProperties(post, new PostDTO(), PostDTO.class, true);
    }

    @Override
    public Boolean createPost(CreatePostRequest request) {
        try {
            Post post = new Post();
            post.setTitle(request.getTitle());
            post.setBrief(request.getBrief());
            post.setContent(request.getContent());
            post.setIsVisible(request.getIsVisible());
            post.setReadNumber(0L);

            postRepository.save(post);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean updatePost(Long id, CreatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Không tìm thấy bài post với id:" + id));
        post.setTitle(request.getTitle());
        post.setBrief(request.getBrief());
        post.setContent(request.getContent());
        post.setIsVisible(request.getIsVisible());

        postRepository.save(post);
        return true;
    }

    @Override
    public Boolean deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Không tìm thấy bài post với id:" + id));
        if (post != null) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PostDTO convertEntityToDTO(Post post) {
        return ObjectUtil.copyProperties(post, new PostDTO(), PostDTO.class, true);
    }
}
