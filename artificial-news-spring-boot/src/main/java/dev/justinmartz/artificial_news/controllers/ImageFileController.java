package dev.justinmartz.artificial_news.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/photos")
public class ImageFileController {
  private final Path uploadDirectory;

  public ImageFileController(@Value("${image.upload.dir}") String uploadDirectory) {
    this.uploadDirectory = Paths.get(uploadDirectory).toAbsolutePath().normalize();
  }

  @GetMapping("/{filename:.+}")
  public ResponseEntity<Resource> getArticlePhoto(@PathVariable String filename) {
    try {
      Path filePath = uploadDirectory.resolve(filename).normalize();
      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists()) {
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (IOException ex) {
      return ResponseEntity.badRequest().build();
    }
  }
}
