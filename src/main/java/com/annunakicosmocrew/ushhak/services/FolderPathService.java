package com.annunakicosmocrew.ushhak.services;

import com.annunakicosmocrew.ushhak.db.DatabaseManager;
import com.annunakicosmocrew.ushhak.db.EntityManagerWrapper;
import com.annunakicosmocrew.ushhak.models.FolderPath;
import com.annunakicosmocrew.ushhak.repositories.FolderPathRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.annunakicosmocrew.ushhak.util.FileUtil.calculateMaxDepth;


@Service
public class FolderPathService {

    private static final Logger logger = LoggerFactory.getLogger(FolderPathService.class);
    private final FolderPathRepository folderPathRepository;

    @Autowired
    public FolderPathService(FolderPathRepository folderPathRepository) {
        this.folderPathRepository = folderPathRepository;
    }


    /**
     * Adds a path to the database.
     */
    public Optional<FolderPath> createPath(FolderPath folderPath) {
        return folderPathRepository.findByPath(folderPath.getPath())
                .map(existingFolderPath -> {
                    logger.info("Folder path {} already exists", folderPath.getPath());
                    return Optional.<FolderPath>empty();
                })
                .orElseGet(() -> {
                    folderPathRepository.save(folderPath);
                    return Optional.of(folderPath);
                });
    }


    /**
     * Checks if the directory exists and is empty.
     *
     * @param path the path to be checked
     * @return true if the directory exists and is empty, false otherwise
     */
    public static boolean isDirectoryExitsEmpty(Path path) {
        if (Files.exists(path) && Files.isDirectory(path)) {
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
                return !dirStream.iterator().hasNext();
            } catch (IOException e) {
                logger.error("Failed to check if directory is empty", e);
                System.out.println("Failed to check if directory is empty");
            }
        }
        return false;
    }


    /**
     * *Returns all the parent folder paths from the database.
     *
     * @return a list of folder paths
     */
    public List<FolderPath> getAllParentFolderPaths() {
        List<FolderPath> folderPaths = null;

        try (EntityManagerWrapper emWrapper = new EntityManagerWrapper(DatabaseManager.getEntityManager())) {
            EntityManager em = emWrapper.getEntityManager();
            em.getTransaction().begin();

            TypedQuery<FolderPath> query = em.createQuery("SELECT f FROM FolderPath f", FolderPath.class);
            folderPaths = query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Failed to fetch folder paths from database", e);
        }

        if (folderPaths != null) {
            return filterParentDirectories(folderPaths);
        }

        return folderPaths;
    }

    /**
     * Filters the parent directories from the given list of paths.
     *
     * @param allPaths the list of paths to be filtered
     */
    private List<FolderPath> filterParentDirectories(List<FolderPath> allPaths) {
        return allPaths.stream()
                .filter(current -> allPaths.stream()
                        .noneMatch(other -> !current.equals(other) && current.getPath().startsWith(other.getPath())))
                .toList();
    }

    /**
     * Returns the absolute path for the given path.
     *
     * @param path the path to be checked
     * @return the absolute path
     */
    public Path getAbsolutePath(Path path) {
        return path.isAbsolute() ? path : path.toAbsolutePath();
    }

    /**
     * Checks if the path exists.
     *
     * @param path the path to be checked
     * @return true if the path exists, false otherwise
     */
    public boolean pathExists(Path path) {
        return Files.exists(path);
    }

    /**
     * Deletes a file or directory at the given path.
     *
     * @param path the path to be deleted
     * @throws IOException if an I/O error occurs
     */
    public void delete(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    /**
     * Returns the folder path object for the given path's parent directory.
     *
     * @param path the path to be checked
     * @return the folder path object
     */
    public FolderPath getByPath(String path) {
        Path folderPathToAdd = getAbsolutePath(Paths.get(path).getParent());

        return getByPath(folderPathToAdd);
    }

    /**
     * Returns the folder path object for the given path.
     *
     * @param path the path to be checked
     * @return the folder path object
     */
    public FolderPath getByPathNoParent(String path) {
        Path folderPathToAdd = getAbsolutePath(Paths.get(path));

        return getByPath(folderPathToAdd);
    }

    /**
     * Returns the folder path object for the given path.
     *
     * @param path the path to be checked
     * @return the folder path object
     */
    public FolderPath getByPath(Path path) {
        FolderPath folderPath = null;

        try (EntityManagerWrapper emWrapper = new EntityManagerWrapper(DatabaseManager.getEntityManager())) {
            EntityManager em = emWrapper.getEntityManager();
            em.getTransaction().begin();

            TypedQuery<FolderPath> query = em.createQuery("SELECT f FROM FolderPath f WHERE f.path = :path", FolderPath.class);
            query.setParameter("path", path.toString());
            folderPath = query.getSingleResult();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.printf("Failed to fetch folder %s path from database%n", path);
            logger.error("Failed to fetch folder {} path from database", folderPath, e);
            return folderPath;
        }

        return folderPath;
    }

    /**
     * Sets the max depth for the given folder path.
     *
     * @param folderPath the folder path to be updated
     */
    public void setMaxDepth(FolderPath folderPath) {
        EntityManager em = null;
        try {
            em = DatabaseManager.getEntityManager();
            em.getTransaction().begin();

            Path path = Paths.get(folderPath.getPath());
            folderPath.setMaxDepth(calculateMaxDepth(path));

            em.merge(folderPath);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Failed to set max depth", e);
            System.out.println("Failed to set max depth");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}


