package app.project.gamestart.web.validators.annotations;

import app.project.gamestart.web.validators.FileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileValidator.class })
@Documented
public @interface NotEmptyFile {

        String message() default "File is required !";

        Class<?>[] groups() default { };

        Class<? extends Payload>[] payload() default { };
}
