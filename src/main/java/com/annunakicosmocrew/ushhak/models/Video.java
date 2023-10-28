package com.annunakicosmocrew.ushhak.models;

import com.annunakicosmocrew.ushhak.models.common.OpenableVideo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Video implements OpenableVideo {
    private static final Logger logger = LoggerFactory.getLogger(Video.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer videoId;
    @Column(nullable = false)
    private String fileName;
    private String title;
    @Column(nullable = true)
    private long fileSize;
    private String description;

    // File System Metadata
    @Column(nullable = false)
    private Date dateCreated;
    @Column(nullable = false)
    private Date lastModified;
    private Date lastOpened;
    private Integer timesOpened;
    private Integer rating;
    private String format;

    // Technical Information
    private String codec;
    private String resolution;
    @Column(nullable = true)
    private double frameRate;
    @Column(nullable = true)
    private int bitRate;
    private Duration duration;
    private String aspectRatio;

    // Relationships and other data
    @ManyToOne
    private Studio studio;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoActor> videoActors;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoDirector> videoDirectors;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoTag> videoTags = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "folder_path_id", nullable = false)
    private FolderPath folderPath;

    public String getFolderPathString() {
        return folderPath.getPath();
    }

    public void setFolderPath(FolderPath folderPath) {
        this.folderPath = folderPath;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public void setTimesOpened(Integer timesOpened) {
        this.timesOpened = timesOpened;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public void setTags(Set<VideoTag> tags) {
        this.videoTags = tags;
    }

    @Override
    public String getLastOpenedString() {
        return lastOpened != null ? new SimpleDateFormat("dd.MM.yyyy HH:mm").format(lastOpened) : "";
    }

    public void setVideoTags(Set<VideoTag> videoTags) {
        this.videoTags = videoTags;
    }


    @Override
    public String toString() {
        return "Video: " +
                " - File Name: " + fileName +
                " - Last Opened: " + lastOpened +
                " - Times Opened: " + timesOpened;
    }


}
