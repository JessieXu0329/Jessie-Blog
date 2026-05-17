package com.jessieblog.dto;

import com.jessieblog.entity.Category;

import java.time.LocalDateTime;

/**
 * 文章列表卡片数据，不含正文，减少传输量。
 */
public record PostSummaryDTO(
        Long id,
        String title,
        String summary,
        Category category,
        String moodGradient,
        LocalDateTime createdAt
) {}
