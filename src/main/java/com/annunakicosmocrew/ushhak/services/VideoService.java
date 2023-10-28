package com.annunakicosmocrew.ushhak.services;

import com.annunakicosmocrew.ushhak.models.FolderPath;
import com.annunakicosmocrew.ushhak.models.Video;
import com.annunakicosmocrew.ushhak.models.dto.VideoDTO;
import com.annunakicosmocrew.ushhak.repositories.FolderPathRepository;
import com.annunakicosmocrew.ushhak.repositories.VideoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final FolderPathRepository folderPathRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VideoService(VideoRepository videoRepository, FolderPathRepository folderPathRepository, ModelMapper modelMapper) {
        this.videoRepository = videoRepository;
        this.folderPathRepository = folderPathRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Video createVideo(VideoDTO videoDTO) {
        Video video = toVideo(videoDTO);
        return videoRepository.save(video);
    }

    public Video toVideo(VideoDTO videoDTO) {
        Video video = modelMapper.map(videoDTO, Video.class);
        video.setFolderPath(getOrCreateFolderPath(Paths.get(videoDTO.getFolderPathString())));

        return video;
    }


    private FolderPath getOrCreateFolderPath(Path path) {
        return folderPathRepository.findByPath(path.toString())
                .orElseGet(() -> {
                    FolderPath folderPath = new FolderPath(path);
                    folderPathRepository.save(folderPath);
                    return folderPath;
                });
    }
}
