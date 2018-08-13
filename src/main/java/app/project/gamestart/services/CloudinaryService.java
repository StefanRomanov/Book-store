package app.project.gamestart.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.Future;

public interface CloudinaryService {
    String uploadImage(MultipartFile multipartFile) throws IOException;

    String deleteImage(String imageId);
}