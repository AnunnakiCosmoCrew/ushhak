package com.annunakicosmocrew.ushhak.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class FileMetadataExtractor {

    private FileMetadataExtractor() {
    }

    private static final Logger logger = LoggerFactory.getLogger(FileMetadataExtractor.class);

    /**
     * Extracts metadata from a file.
     * @param filePath The path to the file.
     * @return An array of Date objects where the first element is dateCreated and the second is lastModified.
     */
    public static Date[] extract(Path filePath) {
        Date[] metadata = new Date[2];
        try {
            metadata[0] = new Date(Files.getLastModifiedTime(filePath).toMillis());
            metadata[1] = new Date(Files.getLastModifiedTime(filePath).toMillis());
        } catch (IOException e) {
            logger.error("Failed to get the last modified time for the file {}", filePath, e);
            System.out.println("Failed to get the last modified time for the file " + filePath);
        }
        return metadata;
    }
}

