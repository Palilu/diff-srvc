package com.palilu.diff.service;

import com.palilu.diff.model.OffsetAto;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit tests for OffsetCalculator.
 *
 * @author pmendoza
 * @since 2019-09-07
 */
@SpringBootTest(classes = {OffsetCalculator.class})
public class OffsetCalculatorTest {

    private static final String LEFT = "A00BC00DEF";
    private static final String RIGHT = "1002300456";

    @Autowired
    private OffsetCalculator offsetCalculator;

    @Test
    public void testCalculateOffset() {
        List<OffsetAto> offsets = offsetCalculator.calculateOffsets(LEFT.getBytes(), RIGHT.getBytes());
        assertEquals(3, offsets.size());
        assertEquals(0, offsets.get(0).getPosition());
        assertEquals(1, offsets.get(0).getSize());
        assertEquals("A", offsets.get(0).getLeftChunk());
        assertEquals("1", offsets.get(0).getRightChunk());
        assertEquals(3, offsets.get(1).getPosition());
        assertEquals(2, offsets.get(1).getSize());
        assertEquals("BC", offsets.get(1).getLeftChunk());
        assertEquals("23", offsets.get(1).getRightChunk());
        assertEquals(7, offsets.get(2).getPosition());
        assertEquals(3, offsets.get(2).getSize());
        assertEquals("DEF", offsets.get(2).getLeftChunk());
        assertEquals("456", offsets.get(2).getRightChunk());
    }
}
