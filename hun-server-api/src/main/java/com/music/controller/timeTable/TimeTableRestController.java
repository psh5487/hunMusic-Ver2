package com.music.controller.timeTable;

import com.music.model.timeTable.OperationTimeTable;
import com.music.model.timeTable.StudentTimeTable;
import com.music.service.timeTable.OperationTimeTableService;
import com.music.service.timeTable.StudentTimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/auth/timeTable")
@RequiredArgsConstructor
public class TimeTableRestController {
    private final StudentTimeTableService studentTimeTableService;
    private final OperationTimeTableService operationTimeTableService;

    /* 모든 학생 시간표 */
    @GetMapping("/getStudentTimeTable")
    public ResponseEntity getStudentTimeTable() {
        List<StudentTimeTable> list = studentTimeTableService.findAllStudentTimeTables();
        int listLength = list.size();

        return new ResponseEntity(new TimeTableResult<>(list, listLength), HttpStatus.OK);
    }

    /* 학생 시간표 추가 */
    @PostMapping("/addStudentTimeTable")
    public ResponseEntity addStudentTimeTable(HttpServletRequest request) {
        String value = request.getParameter("sendString");
        StudentTimeTable saved = studentTimeTableService.insertStudentTimeTable(value);

        return new ResponseEntity(saved, HttpStatus.CREATED);
    }

    /* 학생 시간표 삭제 */
    @PostMapping("/deleteStudentTimeTable")
    public ResponseEntity deleteStudentTimeTable(@RequestParam("item_id") Long id) {
        StudentTimeTable deleted = studentTimeTableService.deleteStudentTable(id);
        return new ResponseEntity(deleted, HttpStatus.OK);
    }

    /* 모든 운영 시간표 */
    @GetMapping("/getOperationTimeTable")
    public ResponseEntity getOperationTimeTable() {
        List<OperationTimeTable> operationTimeTables = operationTimeTableService.findAllOperationTimeTables();
        int listLength = operationTimeTables.size();

        return new ResponseEntity(new TimeTableResult<>(operationTimeTables, listLength), HttpStatus.OK);
    }

    /* 운영 시간표 짜기 */
    @PostMapping("/makeOperationTimeTable")
    public ResponseEntity makeOperationTimeTable(@RequestParam("operationTimeTable_name") String name) {
        OperationTimeTable operationTimeTable = operationTimeTableService.makeOperationTimeTable(name);
        return new ResponseEntity(operationTimeTable, HttpStatus.CREATED);
    }

    /* 운영 시간표 삭제 */
    @PostMapping("/deleteOperationTimeTable")
    public ResponseEntity deleteOperationTimeTable(@RequestParam("item_id") Long id) {
        OperationTimeTable deleted = operationTimeTableService.deleteOperationTable(id);
        return new ResponseEntity(deleted, HttpStatus.OK);
    }

}
