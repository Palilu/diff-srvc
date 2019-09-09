package com.palilu.diff.service;

import com.palilu.diff.domain.Diff;
import com.palilu.diff.domain.DiffRepository;

import com.palilu.diff.model.DiffAto;
import com.palilu.diff.model.DiffMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Diff service implementation.
 *
 * @author pmendoza
 * @since 2019-09-04
 */
@Service
@Slf4j
public class DiffServiceImpl implements DiffService {

    @Autowired
    private DiffRepository diffRepo;

    @Autowired
    private OffsetCalculator offsetCalculator;

    @Override
    @Transactional
    public void saveLeftDiffSide(Long diffId, String value) {
        log.info("Saving left side for diffId={}", diffId);
        Diff diff = diffRepo.findById(diffId).orElse(Diff.builder().id(diffId).build());
        diff.setLeft(value);
        diffRepo.save(diff);
        log.info("Successfully saved left side for diffId={}", diffId);
    }

    @Override
    @Transactional
    public void saveRightDiffSide(Long diffId, String value) {
        log.info("Saving right side for diffId={}", diffId);
        Diff diff = diffRepo.findById(diffId).orElse(Diff.builder().id(diffId).build());
        diff.setRight(value);
        diffRepo.save(diff);
        log.info("Successfully saved right side for diffId={}", diffId);
    }

    @Override
    @Transactional
    public Optional<DiffAto> processDiffAto(Long diffId) {
        log.info("Processing Diff for diffId={}", diffId);
        DiffAto response = DiffAto.builder().offsets(new ArrayList<>()).build();
        Optional<Diff> diff = diffRepo.findById(diffId);
        // If there's no diff or any of the sides are missing
        if (diff.isEmpty() || diff.get().getLeft() == null || diff.get().getRight() == null) {
            return Optional.empty();
        }
        byte[] leftBytes = diff.get().getLeft().getBytes();
        byte[] rightBytes = diff.get().getRight().getBytes();

        if (Arrays.equals(leftBytes, rightBytes)) {
            log.info("Equal sides for diffId={}", diffId);
            response.setMessage(DiffMessage.EQUALS.getMessage());
        } else if (leftBytes.length != rightBytes.length) {
            log.info("Different sized sides for diffId={}", diffId);
            response.setMessage(DiffMessage.DIFFERENT_SIZE.getMessage());
        } else {
            log.info("Calculating offsets for diffId={}", diffId);
            response.setMessage(DiffMessage.OFFSETS.getMessage());
            response.setOffsets(offsetCalculator.calculateOffsets(leftBytes, rightBytes));
        }
        return Optional.of(response);
    }
}
