package com.jessieblog.controller;

import com.jessieblog.dto.UploadRequest;
import com.jessieblog.entity.MediaEntry;
import com.jessieblog.entity.SportMemory;
import com.jessieblog.entity.TechProject;
import com.jessieblog.repository.MediaEntryRepository;
import com.jessieblog.repository.SportMemoryRepository;
import com.jessieblog.repository.TechProjectRepository;
import com.jessieblog.service.GithubStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 通用 Admin 上传控制器。
 *
 * 前端以 multipart/form-data 发送：
 *   - file: MultipartFile     (图片)
 *   - data: JSON string       (UploadRequest 字段)
 *
 * 路由：POST /api/admin/upload/{section}
 *       section = tech | sports | media
 */
@RestController
@RequestMapping("/api/admin")
public class UploadController {

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    private final GithubStorageService    storageService;
    private final TechProjectRepository   techRepo;
    private final SportMemoryRepository   sportRepo;
    private final MediaEntryRepository    mediaRepo;

    public UploadController(GithubStorageService storageService,
                            TechProjectRepository techRepo,
                            SportMemoryRepository sportRepo,
                            MediaEntryRepository mediaRepo) {
        this.storageService = storageService;
        this.techRepo       = techRepo;
        this.sportRepo      = sportRepo;
        this.mediaRepo      = mediaRepo;
    }

    // ═══════════════════════════════════════════════════════════════
    // 上传（通用入口）
    // ═══════════════════════════════════════════════════════════════
    @PostMapping(value = "/upload/{section}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(
            @PathVariable String section,
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") UploadRequest data) {

        log.info("Upload request: section={}, title={}, file={}", section, data.getTitle(), file.getOriginalFilename());

        try {
            // 上传图片到 GitHub，返回 CDN URL
            String cdnUrl = storageService.upload(file, section);

            // 根据板块写入对应表
            Object saved = switch (section.toLowerCase()) {
                case "tech"   -> saveTechProject(data, cdnUrl);
                case "sports" -> saveSportMemory(data, cdnUrl);
                case "media"  -> saveMediaEntry(data, cdnUrl);
                default -> throw new IllegalArgumentException("Unknown section: " + section);
            };

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "ok", true,
                "section", section,
                "data", saved
            ));
        } catch (Exception e) {
            log.error("Upload failed for section={}: {}", section, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "ok", false,
                "error", e.getMessage()
            ));
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 删除
    // ═══════════════════════════════════════════════════════════════
    @DeleteMapping("/{section}/{id}")
    public ResponseEntity<?> delete(@PathVariable String section, @PathVariable Long id) {
        try {
            switch (section.toLowerCase()) {
                case "tech"   -> techRepo.deleteById(id);
                case "sports" -> sportRepo.deleteById(id);
                case "media"  -> mediaRepo.deleteById(id);
                default -> throw new IllegalArgumentException("Unknown section: " + section);
            }
            return ResponseEntity.ok(Map.of("ok", true, "deleted", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "ok", false, "error", e.getMessage()
            ));
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 列表查询（各板块公开读取）
    // ═══════════════════════════════════════════════════════════════
    @GetMapping("/tech")
    public List<TechProject> listTech() {
        return techRepo.findAll();
    }

    @GetMapping("/tech/{id}")
    public ResponseEntity<?> getTech(@PathVariable Long id) {
        return techRepo.findById(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ok", false, "error", "Project not found")));
    }

    @GetMapping("/sports")
    public List<SportMemory> listSports() {
        return sportRepo.findAll();
    }

    @GetMapping("/media")
    public List<MediaEntry> listMedia(@RequestParam(required = false) String category) {
        if (category != null) {
            return mediaRepo.findByCategory(category.toUpperCase());
        }
        return mediaRepo.findAll();
    }

    // ═══════════════════════════════════════════════════════════════
    // 私有保存方法
    // ═══════════════════════════════════════════════════════════════
    private TechProject saveTechProject(UploadRequest data, String cdnUrl) {
        TechProject p = new TechProject();
        p.setTitle(data.getTitle());
        p.setDescription(data.getDescription());
        p.setTechStack(data.getTechStack());
        p.setGithubUrl(data.getGithubUrl());
        p.setRole(data.getRole());
        p.setStatus(data.getStatus() != null ? data.getStatus() : "IN_PROGRESS");
        p.setYear(data.getYear());
        p.setScreenshotUrl(cdnUrl);
        return techRepo.save(p);
    }

    private SportMemory saveSportMemory(UploadRequest data, String cdnUrl) {
        SportMemory m = new SportMemory();
        m.setTitle(data.getTitle());
        m.setLocation(data.getLocation());
        m.setDate(data.getDate());
        m.setDescription(data.getDescription());
        m.setMood(data.getMood());
        m.setPhotoUrl(cdnUrl);
        return sportRepo.save(m);
    }

    private MediaEntry saveMediaEntry(UploadRequest data, String cdnUrl) {
        MediaEntry e = new MediaEntry();
        e.setTitle(data.getTitle());
        e.setSubtitle(data.getSubtitle());
        e.setReview(data.getReview());
        e.setCategory(data.getCategory().toUpperCase());
        e.setCoverUrl(cdnUrl);
        return mediaRepo.save(e);
    }
}
