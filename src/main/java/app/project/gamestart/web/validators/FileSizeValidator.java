package app.project.gamestart.web.validators;

import app.project.gamestart.web.validators.annotations.FileSizeValidation;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSizeValidation, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) return true;

        return value.getSize() <= 10485760;
    }
}
