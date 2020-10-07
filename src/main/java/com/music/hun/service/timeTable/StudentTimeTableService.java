package com.music.hun.service.timeTable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.music.hun.model.timeTable.StudentTimeTable;
import com.music.hun.model.timeTable.TimeTableRequest;
import com.music.hun.repository.StudentTimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentTimeTableService {
    @Autowired
    private StudentTimeTableRepository studentTimeTableRepository;

    /* 모든 학생 시간표 찾기 */
    public List<StudentTimeTable> findAllStudentTimeTables() {
        return studentTimeTableRepository.findAll();
    }

    /* 학생 시간표 삽입 */
    public StudentTimeTable insertStudentTimeTable(String value) {
        // json 문자열인 value 를 json 객체로 변환 (gson library)
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObj = (JsonObject) jsonParser.parse(value);

        // 시간표 이름 받기
        String name = jsonObj.get("name").getAsString();

        // 시간표 받기
        // ex) {"M2", "TH3", "F5", "F6"}
        JsonArray memberArray = (JsonArray) jsonObj.get("classTime");

        // String M1, M2, ..., F5 정해주기 - 스케줄 있으면 1로 체킹
        Map<String, String[]> timeTable = new HashMap<>();
        timeTable.put("M", new String[]{"0", "0", "0", "0", "0", "0"});
        timeTable.put("T", new String[]{"0", "0", "0", "0", "0", "0"});
        timeTable.put("W", new String[]{"0", "0", "0", "0", "0", "0"});
        timeTable.put("TH", new String[]{"0", "0", "0", "0", "0", "0"});
        timeTable.put("F", new String[]{"0", "0", "0", "0", "0", "0"});

        for (int i = 0; i < memberArray.size(); i++) {
            String str = memberArray.get(i).getAsString();
            String key;

            if (str.length() == 3) {
                key = "TH";
            } else{
                key = str.substring(0, 1);
            }

            // 해당 시간 1로 표시
            String time = str.substring(str.length() - 1);

            if (!time.equals("6")) {
                timeTable.get(key)[Integer.parseInt(time)] = "1";
            }

            // M3에 수업인 사람은 2시 45분에 떠나야하므로, M2도 스케줄 있는 거로 간주
            if (time.equals("3")) {
                timeTable.get(key)[2] = "1";
            }
            // 마찬가지로 M6에 수업인 사람은 5시 45분에 떠나야하므로, M5도 스케줄 있는 거로 간주
            else if (time.equals("6")) {
                timeTable.get(key)[5] = "1";
            }
        }

        StudentTimeTable studentTimeTable = StudentTimeTable.builder()
                .name(name)

                .M1(timeTable.get("M")[1])
                .M2(timeTable.get("M")[2])
                .M3(timeTable.get("M")[3])
                .M4(timeTable.get("M")[4])
                .M5(timeTable.get("M")[5])

                .T1(timeTable.get("T")[1])
                .T2(timeTable.get("T")[2])
                .T3(timeTable.get("T")[3])
                .T4(timeTable.get("T")[4])
                .T5(timeTable.get("T")[5])

                .W1(timeTable.get("W")[1])
                .W2(timeTable.get("W")[2])
                .W3(timeTable.get("W")[3])
                .W4(timeTable.get("W")[4])
                .W5(timeTable.get("W")[5])

                .TH1(timeTable.get("TH")[1])
                .TH2(timeTable.get("TH")[2])
                .TH3(timeTable.get("TH")[3])
                .TH4(timeTable.get("TH")[4])
                .TH5(timeTable.get("TH")[5])

                .F1(timeTable.get("F")[1])
                .F2(timeTable.get("F")[2])
                .F3(timeTable.get("F")[3])
                .F4(timeTable.get("F")[4])
                .F5(timeTable.get("F")[5])

                .registeredAt(LocalDateTime.now())
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
