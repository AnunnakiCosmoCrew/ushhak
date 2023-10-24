package com.annunakicosmocrew.ushhak.services;

import com.annunakicosmocrew.ushhak.models.Video;
import com.annunakicosmocrew.ushhak.models.dto.VideoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Optional;

@Service
public class FileMetadataService {

    private static final Logger logger = LoggerFactory.getLogger(FileMetadataService.class);
    public static Optional<VideoDTO> getVideoMetadata(VideoDTO videoDTO) {
        Path filePath = Paths.get(videoDTO.getFolderPathString(), videoDTO.getFileName());

        try {
            BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);

            videoDTO.setDateCreated(new Date(attr.creationTime().toMillis()));
            videoDTO.setLastModified(new Date(attr.lastModifiedTime().toMillis()));
            videoDTO.setLastOpened(new Date(attr.lastAccessTime().toMillis()));
            return Optional.of(videoDTO);

        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

}
