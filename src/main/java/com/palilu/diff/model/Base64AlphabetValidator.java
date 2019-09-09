package com.palilu.diff.model;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author pmendoza
 * @since 2019-09-04
 */
public class Base64AlphabetValidator implements ConstraintValidator<Base64Alphabet, String> {

    @Override
    public boolean isValid(String contactField,
                           ConstraintValidatorContext cxt) {
        return Base64.isBase64(contactField.getBytes());
    }

}
