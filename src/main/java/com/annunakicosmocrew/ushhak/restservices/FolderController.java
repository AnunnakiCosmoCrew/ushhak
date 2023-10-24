package com.annunakicosmocrew.ushhak.restservices;

import com.annunakicosmocrew.ushhak.models.FolderPath;
import com.annunakicosmocrew.ushhak.restservices.response.ApiResponse;
import com.annunakicosmocrew.ushhak.services.FolderPathService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/folder")
public class FolderController {
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
    private final FolderPathService folderPathService;

    @Autowired
    public FolderController(FolderPathService folderPathService) {
        this.folderPathService = folderPathService;
    }

    @PostMapping("/add-folder")
    public ResponseEntity<ApiResponse<FolderPath>> addFolder(@Valid @RequestBody FolderPath folderPath) {
        Optional<FolderPath> savedOrExistingFolderPath = folderPathService.createPath(folderPath);
        return savedOrExistingFolderPath.map(path -> ResponseEntity.ok(new ApiResponse<>(path)))
                .orElseGet(() -> ResponseEntity.badRequest().body(new ApiResponse<>("The folder path already exists.")));
    }

}
