package com.jessieblog.dto;

import com.jessieblog.entity.Category;

import java.time.LocalDateTime;

/**
 * 文章详情，含 Markdown 正文。
 */
public record PostDetailDTO(
        Long id,
        String title,
        String summary,
        String content,
        Category category,
        String moodGradient,
        LocalDateTime createdAt
) {}
