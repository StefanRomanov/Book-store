package app.project.gamestart.web.validators.annotations;

import app.project.gamestart.web.validators.FileSizeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileSizeValidator.class })
@Documented
public @interface FileSizeValidation {
    String message() default "Files should be maximum 10MB !";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
