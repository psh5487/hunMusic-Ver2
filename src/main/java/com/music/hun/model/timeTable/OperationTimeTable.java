package com.music.hun.model.timeTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OperationTimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // 운영시간표 이름 (ex. 학기명)

    private String M1;
    private String M2;
    private String M3;
    private String M4;
    private String M5;

    private String T1;
    private String T2;
    private String T3;
    private String T4;
    private String T5;

    private String W1;
    private String W2;
    private String W3;
    private String W4;
    private String W5;

    private String TH1;
    private String TH2;
    private String TH3;
    private String TH4;
    private String TH5;

    private String F1;
    private String F2;
    private String F3;
    private String F4;
    private String F5;
}
