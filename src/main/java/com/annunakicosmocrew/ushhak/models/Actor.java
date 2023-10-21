package com.annunakicosmocrew.ushhak.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Getter
@Setter
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer actorId;
    private String actorName;
    private String gender;

    @OneToMany(mappedBy = "actor")
    private Set<VideoActor> videoActors;

}
