package com.music.hun.repository;

import com.music.hun.model.timeTable.StudentTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentTimeTableRepository extends JpaRepository<StudentTimeTable, Long> {
    StudentTimeTable findFirstById(Long id);
}
