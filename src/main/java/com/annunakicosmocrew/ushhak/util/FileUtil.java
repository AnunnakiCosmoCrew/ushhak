package com.annunakicosmocrew.ushhak.util;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtil {
    private FileUtil() {
    }

    private static final Tika tika = new Tika();
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


    public static boolean isVideoFile(Path path){
        File file = path.toFile();
        return isVideoFile(file);
    }

    public static boolean isVideoFile(File file) {
        try {
            String mimeType = tika.detect(file);
            return mimeType.startsWith("video");
        } catch (IOException e) {
            logger.error("{} could not be read or is corrupted.", file.getName(), e);
            return false;
        }
    }

    public static int calculateMaxDepth(Path path) {
        final int[] localMaxDepth = {0};
        try {
            Files.walkFileTree(path, new FileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    int currentDepth = dir.getNameCount() - path.getNameCount();
                    localMaxDepth[0] = Math.max(localMaxDepth[0], currentDepth);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.error("Failed to calculate max depth", e);
            System.out.println("Failed to calculate max depth");
        }
        return localMaxDepth[0];
    }


}
