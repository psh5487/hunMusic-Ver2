package com.music.repository;

import com.music.model.timeTable.OperationTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTimeTableRepository extends JpaRepository<OperationTimeTable, Long> {
    OperationTimeTable findFirstById(Long id);
}
