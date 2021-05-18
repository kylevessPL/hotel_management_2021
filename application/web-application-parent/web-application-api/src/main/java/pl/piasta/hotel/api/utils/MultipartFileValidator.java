package pl.piasta.hotel.api.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class MultipartFileValidator implements ConstraintValidator<ValidateMultipartFile, MultipartFile> {

    private List<String> acceptedTypes;

    @Override
    public void initialize(final ValidateMultipartFile constraintAnnotation) {
        acceptedTypes = Arrays.asList(constraintAnnotation.acceptedTypes());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return acceptedTypes.contains(file.getContentType());
    }
}
