-- ═══════════════════════════════════════════════════════════════
-- JessieBlog — 四板块数据库 Schema
-- ═══════════════════════════════════════════════════════════════

-- 1. TECH — 项目实践
CREATE TABLE IF NOT EXISTS tech_projects (
    id            BIGINT          AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(200)    NOT NULL,
    description   TEXT,
    tech_stack    VARCHAR(500)    COMMENT 'Comma-separated tech tags',
    github_url    VARCHAR(500),
    role          VARCHAR(200),
    status        VARCHAR(50)     DEFAULT 'IN_PROGRESS' COMMENT 'DEPLOYED | IN_PROGRESS',
    year          VARCHAR(10),
    screenshot_url VARCHAR(500)   COMMENT 'CDN URL for project screenshot',
    created_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. SPORTS — 运动记忆
CREATE TABLE IF NOT EXISTS sport_memories (
    id            BIGINT          AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(200)    NOT NULL,
    location      VARCHAR(200),
    date          VARCHAR(20),
    description   TEXT            COMMENT 'one-line vibe',
    mood          VARCHAR(100),
    photo_url     VARCHAR(500)    COMMENT 'CDN URL for sport photo',
    created_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. MEDIA — 音乐唱片 + 电影海报
CREATE TABLE IF NOT EXISTS media_entries (
    id            BIGINT          AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(200)    NOT NULL,
    subtitle      VARCHAR(200)    COMMENT 'artist / director',
    cover_url     VARCHAR(500)    COMMENT 'CDN URL for album or poster cover',
    review        TEXT            COMMENT 'one-line comment',
    category      VARCHAR(20)     NOT NULL COMMENT 'MUSIC | MOVIE',
    created_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
