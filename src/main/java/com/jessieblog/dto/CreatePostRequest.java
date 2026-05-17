package com.jessieblog.dto;

import com.jessieblog.entity.Category;

public record CreatePostRequest(
        String title,
        String summary,
        String content,
        Category category,
        String moodGradient
) {}
