package com.music.hun.service;

import com.music.hun.model.timeTable.StudentTimeTable;
import com.music.hun.model.timeTable.TimeTableRequest;
import com.music.hun.repository.StudentTimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentTimeTableService {
    @Autowired
    private StudentTimeTableRepository studentTimeTableRepository;

    /* 모든 학생 시간표 찾기 */
    public List<StudentTimeTable> findAllStudentTimeTables() {
        return studentTimeTableRepository.findAll();
    }

    /* 학생 시간표 삽입 */
    public StudentTimeTable insertStudentTimeTable(TimeTableRequest timeTableRequest) {
        StudentTimeTable studentTimeTable = StudentTimeTable.builder()
                .name(timeTableRequest.getName())

                .M1(timeTableRequest.getM1())
                .M2(timeTableRequest.getM2())
                .M3(timeTableRequest.getM3())
                .M4(timeTableRequest.getM4())
                .M5(timeTableRequest.getM5())

                .T1(timeTableRequest.getT1())
                .T2(timeTableRequest.getT2())
                .T3(timeTableRequest.getT3())
                .T4(timeTableRequest.getT4())
                .T5(timeTableRequest.getT5())

                .W1(timeTableRequest.getW1())
                .W2(timeTableRequest.getW2())
                .W3(timeTableRequest.getW3())
                .W4(timeTableRequest.getW4())
                .W5(timeTableRequest.getW5())

                .TH1(timeTableRequest.getTH1())
                .TH2(timeTableRequest.getTH2())
                .TH3(timeTableRequest.getTH3())
                .TH4(timeTableRequest.getTH4())
                .TH5(timeTableRequest.getTH5())

                .F1(timeTableRequest.getF1())
                .F2(timeTableRequest.getF2())
                .F3(timeTableRequest.getF3())
                .F4(timeTableRequest.getF4())
                .F5(timeTableRequest.getF5())

                .build();
        StudentTimeTable newStudentTimeTable = studentTimeTableRepository.save(studentTimeTable);
        return newStudentTimeTable;
    }

    /* 학생 시간표 삭제 */
    public void deleteStudentTable(Long id) {
        StudentTimeTable studentTimeTable = studentTimeTableRepository.findFirstById(id);
        studentTimeTableRepository.delete(studentTimeTable);
    }
}
