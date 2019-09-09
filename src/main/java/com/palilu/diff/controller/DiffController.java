package com.palilu.diff.controller;

import com.palilu.diff.model.DiffAto;
import com.palilu.diff.model.DiffSideAto;
import com.palilu.diff.service.DiffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author pmendoza
 * @since 2019-09-03
 */
@RestController
@Slf4j
@RequestMapping("/v1/diff/")
public class DiffController {

    @Autowired
    private DiffService diffService;

    @PutMapping(value = "{diffId}/left")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putLeftDiffSide(@PathVariable("diffId") Long diffId,
                                @RequestBody @Valid DiffSideAto left) {
        log.info("Saving left side for diffId={}", diffId);
        diffService.saveLeftDiffSide(diffId, left.getData());
        log.info("Successfully saved left side for diffId={}", diffId);
    }

    @PutMapping(value = "{diffId}/right")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putRightDiffSide(@PathVariable("diffId") Long diffId,
                                 @RequestBody @Valid DiffSideAto right) {
        log.info("Saving right side for diffId={}", diffId);
        diffService.saveRightDiffSide(diffId, right.getData());
        log.info("Successfully saved right side for diffId={}", diffId);
    }

    @GetMapping(value = "{diffId}")
    public ResponseEntity<DiffAto> getDiff(@PathVariable("diffId") Long diffId) {
        log.info("Retrieving diff for diffId={}", diffId);
        return diffService.processDiffAto(diffId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
