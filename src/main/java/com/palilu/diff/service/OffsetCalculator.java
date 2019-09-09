package com.palilu.diff.service;

import com.palilu.diff.model.OffsetAto;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pmendoza
 * @since 2019-09-07
 */
@Component
public class OffsetCalculator {

    /**
     * Calculates offsets between two byte arrays.
     * An Offset between two byte arrays is defined by:
     * - It's position.
     * - It's size.
     * - The left side chunk.
     * - The right side chunk.
     *
     * @param left The left byte array.
     * @param right The right byte array.
     */
    public List<OffsetAto> calculateOffsets(byte[] left, byte[] right) {
        List<OffsetAto> offsets = new ArrayList<>();
        OffsetAto offset = null;
        int size = 0;
        // For each byte position.
        for (int i = 0; i < left.length; i++) {
            // if the elements are different
            boolean different = left[i] != right[i];
            if (different) {
                // If it's the first difference we find
                if (offset == null) {
                    // We create an offset
                    offset = OffsetAto.builder().position(i).build();
                }
                // If not, we just add up to the offset size
                size++;
            }
            // If there's an offset, and we find the end of it or the end of the arrays
            if (offset != null && (!different || i == left.length - 1)) {
                // We set it's size, left and right chunks; add it to the list and reset it
                offset.setSize(size);
                offset.setLeftChunk(getChunk(left, offset.getPosition(), size));
                offset.setRightChunk(getChunk(right, offset.getPosition(), size));
                offsets.add(offset);
                size = 0;
                offset = null;
            }
        }
        return offsets;
    }

    private String getChunk(byte[] array, int position, int size) {
        return new String(ArrayUtils.subarray(array, position, position + size));
    }
}
