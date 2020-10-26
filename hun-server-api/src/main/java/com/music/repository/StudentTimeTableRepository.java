package com.music.repository;

import com.music.model.timeTable.StudentTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentTimeTableRepository extends JpaRepository<StudentTimeTable, Long> {
    StudentTimeTable findFirstById(Long id);
}
