package com.palilu.diff.service;

import com.palilu.diff.domain.Diff;
import com.palilu.diff.domain.DiffRepository;

import com.palilu.diff.model.DiffAto;
import com.palilu.diff.model.DiffMessage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for DiffServiceImpl.
 *
 * @author pmendoza
 * @since 2019-09-07
 */
@SpringBootTest(classes = {DiffServiceImpl.class})
public class DiffServiceImplTest {

    private static final String LEFT = "4oCcSeKAmWxsIGdvIG91dCBhbmQgZmluZCBzb21lIG1vcmUgb2YgdGhhdCBNdWxhbiBTemVjaHVhbiBUZXJpeWFraSBkaXBwaW5nIHNhdWNlLCBNb3J0eS4gQmVjYXVzZSB0aGF04oCZcyB3aGF0IHRoaXMgaXMgYWxsIGFib3V0LCBNb3J0eeKAk3RoYXTigJlzIG15IG9uZS1hcm1lZCBtYW4hIEnigJltIG5vdCBkcml2ZW4gYnkgYXZlbmdpbmcgbXkgZGVhZCBmYW1pbHksIE1vcnR5LiBUaGF0IHdhcyBmYWtlLiBJ4oCZbSBkcml2ZW4gYnkgZmluZGluZyB0aGF0IE1jTnVnZ2V0IHNhdWNlLiBJIHdhbnQgdGhhdCBNdWxhbiBNY051Z2dldCBzYXVjZSwgTW9ydHkuIFRoYXTigJlzIG15IHNlcmllcyBhcmMsIE1vcnR5LiI=";
    private static final String RIGHT = "4oCcSeKAmWxsIGdvIG91dCBhbmQgZmluZCBzb21lIG1vcmUgb2YgdGhhdCBwb2ZmZXJ0amVzIHdpdGggbnV0ZWxsYSBhbmQgaWNlIGNyZWFtLCBNb3J0eS4gQmVjYXVzZSB0aGF04oCZcyB3aGF0IHRoaXMgaXMgYWxsIGFib3V0LCBNb3J0eeKAk3RoYXTigJlzIG15IG9uZS1hcm1lZCBtYW4hIEnigJltIG5vdCBkcml2ZW4gYnkgZHV0Y2ggcXVhbGl0eSBsaWZlIHN0eWxlIE1vcnR5LiBUaGF0IHdhcyBmYWtlLiBJ4oCZbSBkcml2ZW4gYnkgZmluZGluZyB0aG9zZSBwb2ZmZXJ0amVzLiBJIHdhbnQgdGhvc2UgcG9mZmVydGplcyBhbmQgbnV0ZWxsYSwgTW9ydHkuIFRoYXTigJlzIG15IHNlcmllcyBhcmMsIE1vcnR5LiI=";
    private static final String OTHER = "IlBlY3VsaWFyIHRyYXZlbCBzdWdnZXN0aW9ucyBhcmUgZGFuY2luZyBsZXNzb25zIGZyb20gR29kLiI=";

    @Autowired
    private DiffService diffService;

    @MockBean
    private DiffRepository diffRepo;

    @SpyBean
    private OffsetCalculator offsetCalculator;

    @Test
    public void saveLeftDiffSideForNonExistentDiff() {
        diffService.saveLeftDiffSide(42L, LEFT);
        verify(diffRepo).save(any(Diff.class));
    }

    @Test
    public void saveLeftDiffSide() {
        Diff diff = mock(Diff.class);
        when(diffRepo.findById(42L)).thenReturn(Optional.of(diff));
        diffService.saveLeftDiffSide(42L, LEFT);
        verify(diff).setLeft(LEFT);
        verify(diffRepo).save(any(Diff.class));
    }

    @Test
    public void saveRightDiffSideForNonExistentDiff() {
        diffService.saveRightDiffSide(42L, RIGHT);
        verify(diffRepo).save(any(Diff.class));
    }

    @Test
    public void saveRightDiffSide() {
        Diff diff = mock(Diff.class);
        when(diffRepo.findById(42L)).thenReturn(Optional.of(diff));
        diffService.saveRightDiffSide(42L, RIGHT);
        verify(diff).setRight(RIGHT);
        verify(diffRepo).save(any(Diff.class));
    }

    @Test
    public void getDiffAtoForMissingDiff() {
        when(diffRepo.findById(42L)).thenReturn(Optional.empty());
        assertTrue(diffService.processDiffAto(42L).isEmpty());
    }

    @Test
    public void getDiffAtoForMissingLeftSide() {
        Diff diff = Diff.builder().id(42L).right(RIGHT).build();
        when(diffRepo.findById(42L)).thenReturn(Optional.of(diff));
        assertTrue(diffService.processDiffAto(42L).isEmpty());
    }

    @Test
    public void getDiffAtoForMissingRightSide() {
        Diff diff = Diff.builder().id(42L).left(LEFT).build();
        when(diffRepo.findById(42L)).thenReturn(Optional.of(diff));
        assertTrue(diffService.processDiffAto(42L).isEmpty());
    }

    @Test
    public void getDiffAtoForEqualInputs() {
        Diff diff = Diff.builder().id(42L).left(LEFT).right(LEFT).build();
        when(diffRepo.findById(42L)).thenReturn(Optional.of(diff));
        DiffAto response = diffService.processDiffAto(42L).get();
        assertEquals(DiffMessage.EQUALS.getMessage(), response.getMessage());
        assertTrue(response.getOffsets().isEmpty());
    }

    @Test
    public void getDiffAtoForDifferentSizedInputs() {
        Diff diff = Diff.builder().id(42L).left(LEFT).right(OTHER).build();
        when(diffRepo.findById(42L)).thenReturn(Optional.of(diff));
        DiffAto response = diffService.processDiffAto(42L).get();
        assertEquals(DiffMessage.DIFFERENT_SIZE.getMessage(), response.getMessage());
        assertTrue(response.getOffsets().isEmpty());
    }

    @Test
    public void getDiffAtoForOffsets() {
        Diff diff = Diff.builder().id(42L).left(LEFT).right(RIGHT).build();
        when(diffRepo.findById(42L)).thenReturn(Optional.of(diff));
        DiffAto response = diffService.processDiffAto(42L).get();
        assertEquals(DiffMessage.OFFSETS.getMessage(), response.getMessage());
        assertFalse(response.getOffsets().isEmpty());
    }

}
