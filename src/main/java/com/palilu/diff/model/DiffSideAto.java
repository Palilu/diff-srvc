package com.palilu.diff.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Diff side API transfer object.
 *
 * @author pmendoza
 * @since 2019-09-04
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiffSideAto {

    @Base64Alphabet
    private String data;
}
