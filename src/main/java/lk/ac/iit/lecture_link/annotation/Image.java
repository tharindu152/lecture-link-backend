package lk.ac.iit.lecture_link.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ImageConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Image {
    long maximumFileSize() default 3 * 1024 * 1024;

    String message() default "Invalid image file or file size exceeds the maximum file size of {maximumFileSize} Kb";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
