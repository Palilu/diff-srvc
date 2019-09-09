package com.palilu.diff.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pmendoza
 * @since 2019-09-04
 */
@Documented
@Constraint(validatedBy = Base64AlphabetValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Base64Alphabet {

    String message() default "Input contains invalid characters within the Base64 alphabet";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}