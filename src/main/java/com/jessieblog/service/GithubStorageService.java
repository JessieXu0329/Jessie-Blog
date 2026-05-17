package com.jessieblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

/**
 * 将图片上传至 GitHub 仓库，返回 jsDelivr CDN URL。
 *
 * 目标仓库：JessieXu0329/picsforJessie-sBlog
 * CDN 格式：https://cdn.jsdelivr.net/gh/JessieXu0329/picsforJessie-sBlog@main/{path}
 */
@Service
public class GithubStorageService {

    private static final Logger log = LoggerFactory.getLogger(GithubStorageService.class);

    private static final String API_URL  = "https://api.github.com/repos/JessieXu0329/picsforJessie-sBlog/contents/";
    private static final String CDN_BASE = "https://cdn.jsdelivr.net/gh/JessieXu0329/picsforJessie-sBlog@main/";

    @Value("${app.github-token}")
    private String githubToken;

    private final RestTemplate restTemplate;

    public GithubStorageService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * @param file  图片文件
     * @param folder 存储子目录，如 "tech" / "sports" / "media" / "essays"
     * @return jsDelivr CDN URL
     */
    public String upload(MultipartFile file, String folder) throws IOException {
        // 生成唯一文件名：{date}/{uuid}.{ext}
        String datePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String ext = getExtension(file.getOriginalFilename());
        String fileName = datePrefix + "/" + UUID.randomUUID() + "." + ext;

        String path = folder + "/" + fileName;
        String contentBase64 = Base64.getEncoder().encodeToString(file.getBytes());

        Map<String, Object> body = Map.of(
            "message", "upload: " + path,
            "content", contentBase64,
            "branch",  "main"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(githubToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                API_URL + path, HttpMethod.PUT, entity, String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                String cdnUrl = CDN_BASE + path;
                log.info("Uploaded to GitHub: {} -> {}", path, cdnUrl);
                return cdnUrl;
            }
            throw new IOException("GitHub API returned " + response.getStatusCode());
        } catch (Exception e) {
            log.error("GitHub upload failed for {}: {}", path, e.getMessage());
            throw new IOException("Failed to upload image to GitHub", e);
        }
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) return "png";
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
    }
}
