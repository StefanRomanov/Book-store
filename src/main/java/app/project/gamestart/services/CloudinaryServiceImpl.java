package app.project.gamestart.services;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String uploadImage(File file) throws IOException {

        Map<String,String> options = new HashMap<>();
        options.put("resource_type", "raw");
        options.put("type", "authenticated");
        options.put("access_mode", "authenticated");


        String result = this.cloudinary
                .uploader()
                .upload(file, options)
                .get("url")
                .toString();

        file.delete();
        return result;
    }

    @Override
    public Set<String> uploadManyImages(Set<File> files) throws IOException {

        Set<String> result = new HashSet<>();

        if(files != null && files.size() > 0){
            for (File file : files) {
                result.add(uploadImage(file));
            }
        }

        return result;
    }


    @Override
    public String deleteImage(String imageId){
        return null;
    }
}
