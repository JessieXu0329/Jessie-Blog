package com.jessieblog.repository;

import com.jessieblog.entity.MediaEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MediaEntryRepository extends JpaRepository<MediaEntry, Long> {
    List<MediaEntry> findByCategory(String category);
}
