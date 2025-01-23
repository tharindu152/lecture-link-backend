package lk.ac.iit.lecture_link.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageConstraintValidator implements ConstraintValidator<Image, MultipartFile> {

    private long maximumFileSize;

    @Override
    public void initialize(Image constraintAnnotation) {
        maximumFileSize = constraintAnnotation.maximumFileSize();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null || multipartFile.isEmpty()) return true;
        if (multipartFile.getContentType() == null || !multipartFile.getContentType().startsWith("image/")) return false;
        return multipartFile.getSize() <= maximumFileSize;
    }
}
