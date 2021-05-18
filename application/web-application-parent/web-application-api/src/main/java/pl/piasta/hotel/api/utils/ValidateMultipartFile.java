package pl.piasta.hotel.api.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = MultipartFileValidator.class)
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface ValidateMultipartFile {

    String[] acceptedTypes();

    String message() default "File type not supported";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
