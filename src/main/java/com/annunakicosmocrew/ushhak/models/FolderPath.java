package com.annunakicosmocrew.ushhak.models;

import com.annunakicosmocrew.ushhak.util.FileUtil;
import jakarta.persistence.*;

import java.nio.file.Path;
import java.util.Set;

@Entity
public class FolderPath {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer pathId;
    @Column(unique = true)
    String path;
    @Column
    Integer maxDepth;
    @OneToMany(mappedBy = "folderPath")
    private Set<Video> videos;

    public FolderPath(Path path) {
        this.path = path.toString();
        this.maxDepth = calculateMaxDepth(path);
    }

    private int calculateMaxDepth(Path path) {
        final int[] localMaxDepth = {0};
        FileUtil.calculateMaxDepth(path);
        return localMaxDepth[0];
    }

    public FolderPath() {
    }

    @Override
    public String toString() {
        return "FolderPath{" +
                "path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
}
