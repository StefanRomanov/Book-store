package app.project.gamestart.services;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface CloudinaryService {
    String uploadImage(File file) throws IOException;

    Set<String> uploadManyImages(Set<File> files) throws IOException;

    String deleteImage(String imageId) throws URISyntaxException;
}