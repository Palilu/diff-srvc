package com.palilu.diff.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The different messages a Diff can have.
 *
 * @author pmendoza
 * @since 2019-09-07
 */
@Getter
@AllArgsConstructor
public enum DiffMessage {

    EQUALS("Left and right sides are equals."),
    DIFFERENT_SIZE("Left and right sides are of different sizes."),
    OFFSETS("Left are right sides have offsets.");

    private String message;
}
