package com.annunakicosmocrew.ushhak.restservices;

import com.annunakicosmocrew.ushhak.models.Video;
import com.annunakicosmocrew.ushhak.models.dto.VideoDTO;
import com.annunakicosmocrew.ushhak.services.FileMetadataService;
import com.annunakicosmocrew.ushhak.services.VideoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
    private final VideoService videoService;
    private final FileMetadataService fileMetadataService;

    @Autowired
    public VideoController(VideoService videoService, FileMetadataService fileMetadataService) {
        this.videoService = videoService;
        this.fileMetadataService = fileMetadataService;
    }

    @PostMapping("/add-video")
    public ResponseEntity<Video> addVideo(@Valid @RequestBody VideoDTO videoDTO) {
        VideoDTO updatedVideoDTO = fileMetadataService.getVideoMetadata(videoDTO)
                .map(updatedDTO -> {
                    logger.info("Successfully got metadata for video {}", updatedDTO.getFileName());
                    return updatedDTO;
                })
                .orElseGet(() -> {
                    logger.warn("Failed to get metadata for video {}", videoDTO.getFileName());
                    return videoDTO;
                });
        Video video = videoService.createVideo(updatedVideoDTO);
        return new ResponseEntity<>(video, HttpStatus.CREATED);
    }

}
