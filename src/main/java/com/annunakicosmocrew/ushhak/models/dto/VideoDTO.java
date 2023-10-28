package com.annunakicosmocrew.ushhak.models.dto;

import com.annunakicosmocrew.ushhak.models.Studio;
import com.annunakicosmocrew.ushhak.models.VideoActor;
import com.annunakicosmocrew.ushhak.models.VideoDirector;
import com.annunakicosmocrew.ushhak.models.VideoTag;
import com.annunakicosmocrew.ushhak.models.common.OpenableVideo;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class VideoDTO implements OpenableVideo {
    private Integer videoId;
    private String fileName;
    private String title;
    private long fileSize;
    private String description;

    // File System Metadata
    private Date dateCreated;
    private Date lastModified;
    private Date lastOpened;
    private Integer timesOpened;
    private Integer rating;

    // Technical Information
    private String codec;
    private String resolution;
    private double frameRate;
    private int bitRate;
    private Duration duration;
    private String aspectRatio;
    private String format;

    // Relationships and other data
    private Studio studio;
    private Set<VideoActor> videoActors;
    private Set<VideoDirector> videoDirectors;
    private Set<VideoTag> videoTagsDTO;
    private String folderPathString;


    public String getLastOpenedString() {
        return lastOpened != null ? new SimpleDateFormat("dd.MM.yyyy HH:mm").format(lastOpened) : "";
    }

    public void setLastOpened(Date lastOpened) {
        this.lastOpened = lastOpened;
    }

    public void setTimesOpened(Integer timesOpened) {
        this.timesOpened = timesOpened;
    }

    @Override
    public Integer getVideoId() {
        return videoId;
    }

    @Override
    public Set<VideoTag> getVideoTags() {
        return Collections.emptySet();
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public void setTags(Set<VideoTag> tags) {
        this.videoTagsDTO = tags;
    }

    @Override
    public void setVideoTags(Set<VideoTag> videoTags) {
        this.videoTagsDTO = videoTags;
    }

    @Override
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }


    @Override
    public String toString() {
        return "Video: " +
                " - File Name: " + (getFileName() != null ? getFileName() : "") +
                " - Last Opened: " + (getLastOpened() != null ? getLastOpened() : "") +
                " - Times Opened: " + (getTimesOpened() != null ? getTimesOpened() : "") +
                " - Rating: " + (getRating() != null ? getRating() : "") +
                " - Tags: " + (getVideoTagsDTO() != null ? getVideoTagsDTO() : "");
    }


}
