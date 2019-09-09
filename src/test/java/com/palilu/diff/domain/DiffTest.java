package com.palilu.diff.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author pmendoza
 * @since 2019-09-08
 */
public class DiffTest {

    @Test
    public void onCreate() {
        Diff diff = Diff.builder().id(42L).build();
        diff.onCreate();
        assertEquals(42L, diff.getId());
        assertNotNull(diff.getCreateDate());
        assertNotNull(diff.getUpdateDate());
    }

    @Test
    public void onUpdate() {
        Diff diff = Diff.builder().id(42L).build();
        diff.onUpdate();
        assertEquals(42L, diff.getId());
        assertNotNull(diff.getUpdateDate());
    }
}
