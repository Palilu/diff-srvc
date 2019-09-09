package com.palilu.diff.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for Base64AlphabetValidator.
 *
 * @author pmendoza
 * @since 2019-09-07
 */
@SpringBootTest(classes = {Base64AlphabetValidator.class})
public class Base64AlphabetValidatorTest {

    private static final String VALID = "A string like this";
    private static final String INVALID = "A string like '%this%'";

    @Autowired
    private Base64AlphabetValidator base64AlphabetValidator;

    @Test
    public void testIsValid() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        assertTrue(base64AlphabetValidator.isValid(VALID, context));
        assertFalse(base64AlphabetValidator.isValid(INVALID, context));
    }
}
