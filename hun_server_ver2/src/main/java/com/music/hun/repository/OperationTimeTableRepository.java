package com.music.hun.repository;

import com.music.hun.model.timeTable.OperationTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTimeTableRepository extends JpaRepository<OperationTimeTable, Long> {
    OperationTimeTable findFirstById(Long id);
}
