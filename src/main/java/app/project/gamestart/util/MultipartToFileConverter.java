package app.project.gamestart.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class MultipartToFileConverter {
    public static File convertOne(MultipartFile multipartFile) throws IOException {
        File fileToUpload = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(fileToUpload);

        return fileToUpload;
    }

    public static Set<File> convertMany(Set<MultipartFile> multipartFiles) throws IOException {
        Set<File> result = new HashSet<>();

        if(multipartFiles != null && multipartFiles.size() > 0){
            for (MultipartFile multipartFile : multipartFiles) {
                result.add(convertOne(multipartFile));
            }
        }

        return result;
    }
}
