package com.annunakicosmocrew.ushhak.models.common;

import com.annunakicosmocrew.ushhak.models.Video;
import com.annunakicosmocrew.ushhak.models.VideoTag;
import com.annunakicosmocrew.ushhak.models.dto.VideoDTO;

import java.util.Date;
import java.util.Set;

public interface OpenableVideo {
    Integer getTimesOpened();
    void setTimesOpened(Integer timesOpened);
    Integer getVideoId();
    String getFileName();
    String getFolderPathString();
    Date getLastOpened();
    Integer getRating();
    String getLastOpenedString();
    void setRating(Integer rating);
    void setTags(Set<VideoTag> tags);
    void setVideoTags(Set<VideoTag> videoTags);
    Set<VideoTag> getVideoTags();
    void setDateCreated(Date dateCreated);
    void setLastModified(Date lastModified);

}
