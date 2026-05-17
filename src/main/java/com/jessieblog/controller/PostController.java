package com.jessieblog.controller;

import com.jessieblog.dto.CreatePostRequest;
import com.jessieblog.dto.PostDetailDTO;
import com.jessieblog.dto.PostSummaryDTO;
import com.jessieblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /** 获取所有文章列表（不含正文） */
    @GetMapping
    public ResponseEntity<List<PostSummaryDTO>> listAll() {
        return ResponseEntity.ok(postService.listAll());
    }

    /** 获取单篇文章详情（含正文） */
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    /** 发布新文章（需要 X-Admin-Token 请求头） */
    @PostMapping
    public ResponseEntity<PostDetailDTO> create(@RequestBody CreatePostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(postService.create(request));
    }

    /** 删除文章 */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok(java.util.Map.of("ok", true, "deleted", id));
    }
}
