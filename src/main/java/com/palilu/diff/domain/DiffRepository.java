package com.palilu.diff.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pmendoza
 * @since 2019-09-05
 */
@Repository
public interface DiffRepository extends JpaRepository<Diff, Long> {
}
