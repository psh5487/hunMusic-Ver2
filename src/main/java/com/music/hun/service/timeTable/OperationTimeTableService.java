package com.music.hun.service.timeTable;

import com.music.hun.model.timeTable.OperationTimeTable;
import com.music.hun.model.timeTable.StudentTimeTable;
import com.music.hun.model.timeTable.TimeTableRequest;
import com.music.hun.repository.OperationTimeTableRepository;
import com.music.hun.repository.StudentTimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperationTimeTableService {
    @Autowired
    private OperationTimeTableRepository operationTimeTableRepository;

    @Autowired
    private StudentTimeTableRepository studentTimeTableRepository;

    /* 모든 운영 시간표 찾기 */
    public List<OperationTimeTable> findAllOperationTimeTables() {
        return operationTimeTableRepository.findAll();
    }

    /* 운영 시간표 삭제 */
    public void deleteOperationTable(Long id) {
        OperationTimeTable operationTimeTable = operationTimeTableRepository.findFirstById(id);
        operationTimeTableRepository.delete(operationTimeTable);
    }

    /* 운영 시간표 짜기 */
    public OperationTimeTable makeOperationTimeTable(String operationTimeTableName) {

        // 학생들이 제출한 시간표 다 가져오기
        List<StudentTimeTable> studentTimeTables = studentTimeTableRepository.findAll();

        // 운영시간표 초기화 - ""는 아무도 할당 안됨 의미. 할당되면 학생 이름 들어감
        String[][] operationTable = new String[5][6];

        for(int i = 0; i < operationTable.length; i++) {
            for(int j = 0; j < operationTable[i].length; j++) {
                operationTable[i][j] = "";
            }
        }

        // 학생 당 몇 타임씩 배정되었는지 알려주는 맵 - 학생 이름 : {"", 월요일 배정 시간, 화요일 배정 시간, ..., 금요일 배정 시간}
        Map<String, int[]> stAssignMap = new HashMap<>();

        // 학생 시간표 리스트로 정리
        List<String[][]> studentTimeTablesList = new ArrayList<>();

        for(StudentTimeTable stt : studentTimeTables) {
            // 학생 이름
            String name = stt.getName();

            // 요일 당 배정된 시간 초기화 배열 맵에 넣어주기
            stAssignMap.put(name, new int[5]);

            // 학생 시간표 리스트로 정리
            String[][] possibleTime = {
                    {name, stt.getM1(), stt.getM2(), stt.getM3(), stt.getM4(), stt.getM5()},
                    {"", stt.getT1(), stt.getT2(), stt.getT3(), stt.getT4(), stt.getT5()},
                    {"", stt.getW1(), stt.getW2(), stt.getW3(), stt.getW4(), stt.getW5()},
                    {"", stt.getTH1(), stt.getTH2(), stt.getTH3(), stt.getTH4(), stt.getTH5()},
                    {"", stt.getF1(), stt.getF2(), stt.getF3(), stt.getF4(), stt.getF5()}
            };

            studentTimeTablesList.add(possibleTime);
        }

        // 3타임 연속 되는 학생 찾기
        for(String[][] possibleTime : studentTimeTablesList) {

            String name = possibleTime[0][0];

            for(int i = 0; i < 5; i++) {
                // 3타임짜리니까 주 1회만 배정되도록
                if(count(stAssignMap.get(name), 0) <= 4) {
                    break;
                }

                for(int j = 1; j <= 3; j++) {
                    // 학생 3타임 연속으로 가능하고, 해당 시간에 아무도 배정되어있지 않을 경우
                    if(possibleTime[i][j].equals("0") && possibleTime[i][j+1].equals("0") && possibleTime[i][j+2].equals("0")
                            && operationTable[i][j].equals("") && operationTable[i][j+1].equals("") && operationTable[i][j+2].equals("")) {
                        operationTable[i][j] = name;
                        operationTable[i][j+1] = name;
                        operationTable[i][j+2] = name;

                        stAssignMap.get(name)[i] += 3;
                        break;
                    }
                }
            }
        }

        // 2타임 연속 되는 학생 찾기
        for(String[][] possibleTime : studentTimeTablesList) {
            String name = possibleTime[0][0];

            // 아직 배정 안 받은 학생 대상
            if(count(stAssignMap.get(name), 0) != 5)
                continue;

            for(int i = 0; i < 5; i++) {
                // 2타임짜리니까 주 1회만 배정되도록
                if(count(stAssignMap.get(name), 0) <= 4) {
                    break;
                }

                for(int j = 1; j <= 4; j++) {
                    if(possibleTime[i][j].equals("0") && possibleTime[i][j+1].equals("0")
                            && operationTable[i][j].equals("") && operationTable[i][j+1].equals("")) {
                        operationTable[i][j] = name;
                        operationTable[i][j+1] = name;

                        stAssignMap.get(name)[i] += 2;
                        break;
                    }
                }
            }
        }

        // 1타임 되는 학생 찾기
        for(String[][] possibleTime : studentTimeTablesList) {
            String name = possibleTime[0][0];

            // 아직 배정 안 받은 학생 대상
            if(count(stAssignMap.get(name), 0) != 5)
                continue;

            for(int i = 0; i < 5; i++) {
                // 1타임짜리니까 주 2회까지 배정될 수 있도록
                if(count(stAssignMap.get(name), 0) <= 3) {
                    break;
                }

                for(int j = 1; j <= 5; j++) {
                    if(possibleTime[i][j].equals("0") && operationTable[i][j].equals("")) {
                        operationTable[i][j] = name;

                        stAssignMap.get(name)[i] += 1;
                        break;
                    }
                }
            }
        }

        // 2타임 연속인 사람 중에, 아직 아무도 배정 안 된 타임에 추가해서 넣기
        for(String[][] possibleTime: studentTimeTablesList) {
            String name = possibleTime[0][0];

            if(count(stAssignMap.get(name), 2) < 1)
                continue;

            for(int i = 0; i < 5; i++) {
                // 주 2회까지 배정될 수 있도록
                if(count(stAssignMap.get(name), 0) <= 3) {
                    break;
                }

                for(int j = 1; j <= 5; j++) {
                    if(possibleTime[i][j].equals("0") && operationTable[i][j].equals("")) {
                        operationTable[i][j] = name;

                        stAssignMap.get(name)[i] += 1;
                        break;
                    }
                }
            }
        }

        /* 한 번도 배정 받지 않은 사람 - 3타임 연속으로 배정된 시간을 골라서 넣어주기 */
        // 3타임 연속인 사람 찾고, 각 요일에서 시작 타임 알아내기
        List<String> threeTimeStudents = new ArrayList<>();

        for(String key : stAssignMap.keySet()) {
            int[] arr = stAssignMap.get(key);
            if(count(arr, 3) >= 1)
                threeTimeStudents.add(key);
        }

        List<int[]> startTimes = new ArrayList<>();
        for(String threeTimeStudent: threeTimeStudents) {
            loop:
            for(int j = 1; j <= 5; j++) {
                for(int i = 0; i < 5; i++) {
                    if(operationTable[i][j].equals(threeTimeStudent)) {
                        startTimes.add(new int[]{i, j});
                        break loop;
                    }
                }
            }
        }

        for(String[][] possibleTime : studentTimeTablesList) {
            String name = possibleTime[0][0];

            // 아직 배정 안 받은 학생 대상
            if (count(stAssignMap.get(name), 0) != 5)
                continue;

            // 3타임의 앞, 뒤 시간 할당하기
            for(int[] startTime : startTimes) {
                // 주 2회까지 할당되도록
                if(count(stAssignMap.get(name), 0) <= 3) {
                    break;
                }

                int dayIdx = startTime[0];
                int TimeIdx = startTime[1];

                if(possibleTime[dayIdx][TimeIdx].equals("0")) {
                    operationTable[dayIdx][TimeIdx] = name;
                    stAssignMap.get(name)[dayIdx] += 1;
                } else if(possibleTime[dayIdx][TimeIdx + 2].equals("0")) {
                    operationTable[dayIdx][TimeIdx + 2] = name;
                    stAssignMap.get(name)[dayIdx] += 1;
                }
            }
        }

        /* 한 번도 배정 받지 않은 사람 - 2타임 연속으로 배정된 시간을 골라서 넣어주기 */
        // 2타임 연속인 사람 찾고, 각 요일에서 시작 타임 알아내기
        List<String> twoTimeStudents = new ArrayList<>();

        for(String key : stAssignMap.keySet()) {
            int[] arr = stAssignMap.get(key);
            if(count(arr, 2) >= 1)
                twoTimeStudents.add(key);
        }

        List<int[]> startTimes2 = new ArrayList<>();
        for(String twoTimeStudent: twoTimeStudents) {
            loop:
            for(int j = 1; j <= 5; j++) {
                for(int i = 0; i < 5; i++) {
                    if(operationTable[i][j].equals(twoTimeStudent)) {
                        startTimes2.add(new int[]{i, j});
                        break loop;
                    }
                }
            }
        }

        for(String[][] possibleTime : studentTimeTablesList) {
            String name = possibleTime[0][0];

            // 아직 배정 안 받은 학생 대상
            if (count(stAssignMap.get(name), 0) != 5)
                continue;

            // 2타임의 앞, 뒤 시간 할당하기
            for(int[] startTime : startTimes2) {
                // 주 2회까지 할당되도록
                if(count(stAssignMap.get(name), 0) <= 3) {
                    break;
                }

                int dayIdx = startTime[0];
                int TimeIdx = startTime[1];

                if(possibleTime[dayIdx][TimeIdx].equals("0")) {
                    operationTable[dayIdx][TimeIdx] = name;
                    stAssignMap.get(name)[dayIdx] += 1;
                } else if(possibleTime[dayIdx][TimeIdx + 1].equals("0")) {
                    operationTable[dayIdx][TimeIdx + 1] = name;
                    stAssignMap.get(name)[dayIdx] += 1;
                }
            }
        }


        OperationTimeTable operationTimeTable = OperationTimeTable.builder()
                .name(operationTimeTableName)

                .M1(operationTable[0][1])
                .M2(operationTable[0][2])
                .M3(operationTable[0][3])
                .M4(operationTable[0][4])
                .M5(operationTable[0][5])

                .T1(operationTable[1][1])
                .T2(operationTable[1][2])
                .T3(operationTable[1][3])
                .T4(operationTable[1][4])
                .T5(operationTable[1][5])

                .W1(operationTable[2][1])
                .W2(operationTable[2][2])
                .W3(operationTable[2][3])
                .W4(operationTable[2][4])
                .W5(operationTable[2][5])

                .TH1(operationTable[3][1])
                .TH2(operationTable[3][2])
                .TH3(operationTable[3][3])
                .TH4(operationTable[3][4])
                .TH5(operationTable[3][5])

                .F1(operationTable[4][1])
                .F2(operationTable[4][2])
                .F3(operationTable[4][3])
                .F4(operationTable[4][4])
                .F5(operationTable[4][5])

                .registeredAt(LocalDateTime.now())
                .build();

        OperationTimeTable newOperationTimeTable = operationTimeTableRepository.save(operationTimeTable);
        return newOperationTimeTable;
    }

    // 배열에서 target 원소 개수 세기
    private int count(int[] arr, int target) {
        int count = 0;

        for(int element : arr) {
            if(element == target) {
                count++;
            }
        }

        return count;
    }

}
