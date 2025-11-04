package com.final_exam_springboot.validate;

import jakarta.persistence.Table;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileNotBlankValidator.class})
public @interface FileNotBlank {
    String message() default "File not blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
