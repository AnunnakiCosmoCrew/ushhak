package com.annunakicosmocrew.ushhak.models;

import com.annunakicosmocrew.ushhak.models.common.OpenableVideo;
import com.annunakicosmocrew.ushhak.models.dto.VideoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Setter
@Getter
public class Video implements OpenableVideo {
    private static final Logger logger = LoggerFactory.getLogger(Video.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer videoId;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private Date dateCreated;
    private Date lastOpened;
    @Column(nullable = false)
    private Date lastModified;
    private Integer timesOpened;
    private Integer rating;
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

    public Video() {
    }

    private Video(String fileName, FolderPath folderPath, Date dateCreated, Date lastModified) {
        this.fileName = fileName;
        this.dateCreated = dateCreated;
        this.lastModified = lastModified;
        this.folderPath = folderPath;
    }



    public String getFolderPathString() {
        return folderPath.getPath();
    }

    public void setFolderPath(FolderPath folderPath) {
        this.folderPath = folderPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLastOpened(Date lastOpened) {
        this.lastOpened = lastOpened;
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


    public VideoDTO toDTO(Video video) {
        VideoDTO dto = new VideoDTO();
        dto.setVideoId(video.getVideoId());
        dto.setFileName(video.getFileName());
        dto.setLastOpened(video.getLastOpened());
        dto.setTimesOpened(video.getTimesOpened());
        dto.setRating(video.getRating());
        dto.setVideoTagsDTO(new HashSet<>(video.getVideoTags()));
        Optional.ofNullable(video.getFolderPath())
                .ifPresent(path -> dto.setFolderPathString(path.getPath()));

        return dto;
    }

    @Override
    public String getLastOpenedString() {
        return lastOpened != null ? new SimpleDateFormat("dd.MM.yyyy HH:mm").format(lastOpened) : "";
    }

    public void setVideoTags(Set<VideoTag> videoTags) {
        this.videoTags = videoTags;
    }

    @Override
    public Video toVideo() {
        return this;
    }

    @Override
    public VideoDTO toVideoDTO() {
        return toDTO(this);
    }

    @Override
    public String toString() {
        return "Video: " +
                " - File Name: " + fileName +
                " - Last Opened: " + lastOpened +
                " - Times Opened: " + timesOpened;
    }




}
