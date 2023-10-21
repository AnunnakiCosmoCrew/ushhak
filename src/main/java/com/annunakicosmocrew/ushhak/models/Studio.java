package com.annunakicosmocrew.ushhak.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer studioId;
    private String studioName;

    @OneToMany(mappedBy = "studio")
    private Set<Video> videos;
}
