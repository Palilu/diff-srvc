package com.palilu.diff.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author pmendoza
 * @since 2019-09-07
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiffRepositoryIntegrationTest {

    private static final String LEFT = "Left";
    private static final String RIGHT = "Right";

    @Autowired
    private DiffRepository diffRepo;

    @Test
    @Transactional
    public void testCreate() {
        Diff diff = Diff.builder().id(42L).left(LEFT).right(RIGHT).build();
        diffRepo.save(diff);
        diff = diffRepo.getOne(42L);

        assertEquals((Long) 42L, diff.getId());
        assertEquals(LEFT, diff.getLeft());
        assertEquals(RIGHT, diff.getRight());
        assertNotNull(diff.getCreateDate());
        assertNotNull(diff.getUpdateDate());
    }
}
