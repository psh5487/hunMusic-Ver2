package com.music.hun.controller.hunActivity;

import com.music.hun.model.timeTable.OperationTimeTable;
import com.music.hun.model.timeTable.StudentTimeTable;
import com.music.hun.service.timeTable.OperationTimeTableService;
import com.music.hun.service.timeTable.StudentTimeTableService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TimeTableController {
    private final StudentTimeTableService studentTimeTableService;
    private final OperationTimeTableService operationTimeTableService;

    /* 학생 시간표 입력 페이지 */
    @GetMapping("/studentTimeTable")
    public String studentTimeTable(Model model) {
        List<StudentTimeTable> list = studentTimeTableService.findAllStudentTimeTables();
        int listLength = list.size();

        model.addAttribute("studentTimeTableAll", list);
        model.addAttribute("studentTimeTableLength", listLength);

        return "studentTimeTable";
    }

    /* 학생 시간표 받기 */
    @PostMapping("/addStudentTimeTable")
    public String addStudentTimeTable(HttpServletRequest request) {
        String value = request.getParameter("sendString");
        studentTimeTableService.insertStudentTimeTable(value);

        return "studentTimeTable";
    }

    /* 학생 시간표 삭제 */
    @PostMapping("/deleteStudentTimeTable")
    public String deleteStudentTimeTable(@RequestParam("item_id") Long id) {
        studentTimeTableService.deleteStudentTable(id);
        return "redirect:/studentTimeTable";
    }

    /* 운영 시간표 페이지 */
    @GetMapping("/operationTimeTable")
    public String operationTimeTable(Model model) {
        // 모든 운영 시간표 가져오기
        List<OperationTimeTable> operationTimeTables = operationTimeTableService.findAllOperationTimeTables();

        // 학생들이 제출한 시간표 가져오기 (제출 명단을 위함)
        List<StudentTimeTable> studentTimeTables = studentTimeTableService.findAllStudentTimeTables();

        model.addAttribute("operationTimeTableAll", operationTimeTables);
        model.addAttribute("operationTimeTablesLength", operationTimeTables.size());

        model.addAttribute("studentTimeTableAll", studentTimeTables);
        model.addAttribute("studentTimeTablesLength", studentTimeTables.size());

        return "operationTimeTable";
    }

    /* 운영 시간표 짜기 */
    @PostMapping("/makeOperationTimeTable")
    public String makeOperationTimeTable(@RequestParam("operationTimeTable_name") String name) {
        operationTimeTableService.makeOperationTimeTable(name);
        return "redirect:/operationTimeTable";
    }

    /* 운영 시간표 삭제 */
    @PostMapping("/deleteOperationTimeTable")
    public String deleteOperationTimeTable(@RequestParam("item_id") Long id) {
        operationTimeTableService.deleteOperationTable(id);
        return "redirect:/operationTimeTable";
    }


}
