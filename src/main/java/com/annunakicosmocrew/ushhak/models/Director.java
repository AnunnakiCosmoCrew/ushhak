package com.annunakicosmocrew.ushhak.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer directorId;
    private String directorName;

    @OneToMany(mappedBy = "director")
    private Set<VideoDirector> videoDirectors;
}
