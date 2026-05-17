package com.jessieblog.service;

import com.jessieblog.dto.CreatePostRequest;
import com.jessieblog.dto.PostDetailDTO;
import com.jessieblog.dto.PostSummaryDTO;

import java.util.List;

public interface PostService {

    List<PostSummaryDTO> listAll();

    PostDetailDTO getById(Long id);

    PostDetailDTO create(CreatePostRequest request);

    void delete(Long id);
}
