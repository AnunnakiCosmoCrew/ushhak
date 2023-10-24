package com.annunakicosmocrew.ushhak.services;

import com.annunakicosmocrew.ushhak.models.Video;
import com.annunakicosmocrew.ushhak.models.dto.VideoDTO;
import com.annunakicosmocrew.ushhak.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Transactional
    public Video createVideo(VideoDTO videoDTO) {
        Video video = videoDTO.toVideo();
        return videoRepository.save(video);
    }
}
