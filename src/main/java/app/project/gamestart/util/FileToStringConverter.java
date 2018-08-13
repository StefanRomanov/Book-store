package app.project.gamestart.util;

import app.project.gamestart.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class FileToStringConverter {

    private final CloudinaryService cloudinaryService;

    @Autowired
    public FileToStringConverter(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    public String convertOne(MultipartFile file) throws IOException {

        String url = this.cloudinaryService.uploadImage(file);
        if(url == null){
            throw new IllegalArgumentException("File upload failed.");
        }
        return url;
    }

    public Set<String> convertMany(Set<MultipartFile> files) throws IOException {

        Set<String> urls = new HashSet<>();

        if(files.size() > 0){
            for (MultipartFile file : files) {
                String url = this.cloudinaryService.uploadImage(file);

                if(url == null){
                    throw new IllegalArgumentException("File upload failed.");
                }

                urls.add(url);
            }
        }

        return urls;
    }

}
