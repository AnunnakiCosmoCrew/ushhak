package com.annunakicosmocrew.ushhak.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VideoTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Tag tag;
    @ManyToOne
    private Video video;

    public VideoTag() {
    }

    public VideoTag(Tag tag, Video video) {
        this.tag = tag;
        this.video = video;
    }

    // In VideoTag class
    @Override
    public String toString() {
        return "VideoTag: " +
                " - ID: " + id +
                " - Tag: " + tag;
    }
}
