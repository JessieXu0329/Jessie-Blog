package com.jessieblog.service.impl;

import com.jessieblog.dto.CreatePostRequest;
import com.jessieblog.dto.PostDetailDTO;
import com.jessieblog.dto.PostSummaryDTO;
import com.jessieblog.entity.Post;
import com.jessieblog.exception.PostNotFoundException;
import com.jessieblog.repository.PostRepository;
import com.jessieblog.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostSummaryDTO> listAll() {
        return postRepository.findAllOrderByCreatedAtDesc()
                .stream()
                .map(this::toSummary)
                .toList();
    }

    @Override
    public PostDetailDTO getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return toDetail(post);
    }

    @Override
    @Transactional
    public PostDetailDTO create(CreatePostRequest req) {
        Post post = new Post();
        post.setTitle(req.title());
        post.setSummary(req.summary());
        post.setContent(req.content());
        post.setCategory(req.category());
        post.setMoodGradient(req.moodGradient());
        // createdAt 由 @PrePersist 自动填充
        return toDetail(postRepository.save(post));
    }

    private PostSummaryDTO toSummary(Post p) {
        return new PostSummaryDTO(
                p.getId(), p.getTitle(), p.getSummary(),
                p.getCategory(), p.getMoodGradient(), p.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!postRepository.existsById(id)) throw new PostNotFoundException(id);
        postRepository.deleteById(id);
    }

    private PostDetailDTO toDetail(Post p) {
        return new PostDetailDTO(
                p.getId(), p.getTitle(), p.getSummary(), p.getContent(),
                p.getCategory(), p.getMoodGradient(), p.getCreatedAt()
        );
    }
}
