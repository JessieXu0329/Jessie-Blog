package com.jessieblog.dto;

/**
 * 通用上传 DTO，前端以 JSON part 发送。
 * 各板块只填充自己需要的字段，其余为 null。
 */
public class UploadRequest {

    // ── 所有板块共用 ──────────────────────────────────────
    private String title;

    // ── MEDIA ─────────────────────────────────────────────
    private String subtitle;      // artist / director
    private String review;        // 一句话评价
    private String category;      // MUSIC | MOVIE（MEDIA 专用）

    // ── TECH ──────────────────────────────────────────────
    private String description;
    private String techStack;     // comma-separated
    private String githubUrl;
    private String role;
    private String status;        // DEPLOYED | IN_PROGRESS
    private String year;

    // ── SPORTS ────────────────────────────────────────────
    private String location;
    private String date;
    private String mood;

    // ── Getters / Setters ─────────────────────────────────
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }
}
