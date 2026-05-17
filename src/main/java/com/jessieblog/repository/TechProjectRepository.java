package com.jessieblog.repository;

import com.jessieblog.entity.TechProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechProjectRepository extends JpaRepository<TechProject, Long> {
}
