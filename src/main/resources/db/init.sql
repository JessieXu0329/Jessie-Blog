CREATE DATABASE IF NOT EXISTS jessie_blog
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE jessie_blog;

CREATE TABLE IF NOT EXISTS posts (
    id            BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    title         VARCHAR(255)    NOT NULL                COMMENT '文章标题',
    summary       VARCHAR(500)    NOT NULL DEFAULT ''     COMMENT '摘要',
    content       LONGTEXT        NOT NULL                COMMENT '正文（Markdown）',
    category      ENUM('Tech','Essay','Media')
                                  NOT NULL DEFAULT 'Essay' COMMENT '分类',
    mood_gradient VARCHAR(120)    NOT NULL DEFAULT ''     COMMENT '心情渐变色，存 CSS gradient 值',
    created_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    PRIMARY KEY (id),
    INDEX idx_category (category),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='博客文章';

-- 示例数据
INSERT INTO posts (title, summary, content, category, mood_gradient, created_at) VALUES
(
    '用 Spring Boot 3 搭建个人博客后端',
    '记录从零搭建博客后端的全过程，包含 JPA、Security 等核心模块配置。',
    '## 前言\n\n本文记录使用 Spring Boot 3 + Java 21 搭建博客后端的完整过程。\n\n## 依赖\n\n- Spring Web\n- Spring Data JPA\n- Spring Security',
    'Tech',
    'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    '2026-05-01 10:00:00'
),
(
    '写在博客上线之际',
    '终于有了一个属于自己的小角落，记录生活与思考。',
    '## 你好，世界\n\n这是我的第一篇博文。',
    'Essay',
    'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    '2026-05-12 09:00:00'
);
