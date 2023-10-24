package com.annunakicosmocrew.ushhak.repositories;

import com.annunakicosmocrew.ushhak.models.FolderPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FolderPathRepository extends JpaRepository<FolderPath, Integer> {
    Optional<FolderPath> findByPath(String path);
}
