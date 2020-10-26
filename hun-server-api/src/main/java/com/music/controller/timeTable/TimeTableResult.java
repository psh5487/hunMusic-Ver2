package com.music.controller.timeTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeTableResult<T> {
    private List<T> timeTableList;
    private int listLength;
}
