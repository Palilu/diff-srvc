package com.palilu.diff.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Diff API transfer object.
 *
 * @author pmendoza
 * @since 2019-09-04
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class DiffAto {

    private String message;

    private List<OffsetAto> offsets;
}
