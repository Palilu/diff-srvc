package com.palilu.diff.service;

import com.palilu.diff.model.DiffAto;

import java.util.Optional;

/**
 * @author pmendoza
 * @since 2019-09-04
 */
public interface DiffService {

    /**
     * Set's a Diff's left side value. If the Diff does not exists, it creates it.
     *
     * @param diffId The Diff's ID.
     * @param value The value.
     */
    void saveLeftDiffSide(Long diffId, String value);

    /**
     * Set's a Diff's right side value. If the Diff does not exists, it creates it.
     *
     * @param diffId The Diff's ID.
     * @param value The value.
     */
    void saveRightDiffSide(Long diffId, String value);

    /**
     * Returns a summary of the differences between a Diff's left and right sides.
     *
     * @param diffId The Diff's ID.
     */
    Optional<DiffAto> processDiffAto(Long diffId);
}
