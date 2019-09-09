package com.palilu.diff.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author pmendoza
 * @since 2019-09-07
 */
public class DiffMessageTest {

    @Test
    public void getMessage() {
        assertEquals("Left and right sides are equals.", DiffMessage.EQUALS.getMessage());
        assertEquals("Left and right sides are of different sizes.", DiffMessage.DIFFERENT_SIZE.getMessage());
        assertEquals("Left are right sides have offsets.", DiffMessage.OFFSETS.getMessage());
    }
}
