package com.annunakicosmocrew.ushhak.services;

import com.annunakicosmocrew.ushhak.models.dto.VideoDTO;
import com.annunakicosmocrew.ushhak.util.FileUtil;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;


@Service
public class FileMetadataService {

    private static final Logger logger = LoggerFactory.getLogger(FileMetadataService.class);
    private final Tika tika;

    @Autowired
    public FileMetadataService(Tika tika) {
        this.tika = tika;
    }

    public Optional<VideoDTO> getVideoMetadata(VideoDTO videoDTO) {
        Path filePath = Paths.get(videoDTO.getFolderPathString(), videoDTO.getFileName());
        try {
            BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
            Metadata metadata = extractMetadata(filePath);
            populateVideoDTO(videoDTO, attr, metadata);
            return Optional.of(videoDTO);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private Metadata extractMetadata(Path filePath) throws IOException {
        Metadata metadata = new Metadata();
        try (InputStream is = Files.newInputStream(filePath)) {
            tika.parse(is, metadata);
        }
        return metadata;
    }

    private void populateVideoDTO(VideoDTO videoDTO, BasicFileAttributes attr, Metadata metadata) {
        videoDTO.setDateCreated(new Date(attr.creationTime().toMillis()));
        videoDTO.setLastModified(new Date(attr.lastModifiedTime().toMillis()));
        videoDTO.setLastOpened(new Date(attr.lastAccessTime().toMillis()));
        videoDTO.setFileSize(attr.size());
        videoDTO.setTitle(FileUtil.fileNameToTitle(videoDTO.getFileName()));
        videoDTO.setCodec(metadata.get("video:codec"));
        if (metadata.get("video:width") != null && metadata.get("video:height") != null) videoDTO
                .setResolution(metadata.get("video:width") + "x" + metadata.get("video:height"));
        if (metadata.get("video:duration") != null) videoDTO.setDuration(Duration
                .parse("PT" + metadata.get("video:duration").replace(":", "H")
                        .replace(":", "M") + "S"));
        if (metadata.get("video:bit_rate") != null)
            videoDTO.setBitRate(Integer.parseInt(metadata.get("video:bit_rate")));
        if (metadata.get("video:frame_rate") != null)
            videoDTO.setFrameRate(Double.parseDouble(metadata.get("video:frame_rate")));
        videoDTO.setFormat(metadata.get("video:format"));
        videoDTO.setAspectRatio(metadata.get("video:aspect_ratio"));
    }
}