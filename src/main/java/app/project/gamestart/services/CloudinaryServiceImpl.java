package app.project.gamestart.services;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Future;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Async
    @Override
    public Future<String> uploadImage(MultipartFile multipartFile) throws IOException {
        File fileToUpload = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(fileToUpload);

        String result = this.cloudinary
                .uploader()
                .upload(fileToUpload, new HashMap())
                .get("url")
                .toString();

        return new AsyncResult<>(result);
    }

    @Override
    public String deleteImage(String imageId) {
        return null;
    }
}
