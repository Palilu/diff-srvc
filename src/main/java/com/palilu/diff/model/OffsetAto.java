package com.palilu.diff.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Offset API transfer object.
 *
 * @author pmendoza
 * @since 2019-09-04
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class OffsetAto {

    private int position;

    private int size;

    private String leftChunk;

    private String rightChunk;
}
