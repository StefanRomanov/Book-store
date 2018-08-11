package app.project.gamestart.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

public class TestModel {
    private MultipartFile file;

    public TestModel() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
