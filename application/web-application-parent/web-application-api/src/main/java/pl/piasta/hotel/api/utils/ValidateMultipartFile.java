package pl.piasta.hotel.api.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = MultipartFileValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ValidateMultipartFile {

    String[] acceptedTypes();

    String message() default "File type not supported";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}