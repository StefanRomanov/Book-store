package app.project.gamestart.validators;

import app.project.gamestart.validators.annotations.NotEmptyFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<NotEmptyFile, MultipartFile>{

    @Override
    public void initialize(final NotEmptyFile constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) return true;

        if(value.getSize() == 0){
            return false;
        }
        return true;
    }
}
