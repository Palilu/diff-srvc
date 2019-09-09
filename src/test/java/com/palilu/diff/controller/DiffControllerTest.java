package com.palilu.diff.controller;

import com.palilu.diff.model.DiffAto;
import com.palilu.diff.model.DiffSideAto;
import com.palilu.diff.service.DiffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author pmendoza
 * @since 2019-09-08
 */
@SpringBootTest(classes = {DiffController.class})
public class DiffControllerTest {

    private static final String DATA = "data";

    @Autowired
    private DiffController diffController;

    @MockBean
    private DiffService diffService;

    @Test
    public void putLeftDiffSide() {
        diffController.putLeftDiffSide(42L, DiffSideAto.builder().data(DATA).build());
        verify(diffService, only()).saveLeftDiffSide(42L, DATA);
    }

    @Test
    public void putRightDiffSide() {
        diffController.putRightDiffSide(42L, DiffSideAto.builder().data(DATA).build());
        verify(diffService, only()).saveRightDiffSide(42L, DATA);
    }

    @Test
    public void getDiff() {
        DiffAto diffAto = mock(DiffAto.class);
        when(diffService.processDiffAto(42L)).thenReturn(Optional.of(diffAto));
        ResponseEntity<DiffAto> response = diffController.getDiff(42L);
        assertEquals(diffAto, response.getBody());
    }
}
