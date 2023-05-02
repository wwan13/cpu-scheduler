package com.wwan13.cpuscheduler.Processes;

import lombok.*;

import java.util.List;

/**
 * RequestDto Class
 * Client 에서 받아올 데이터 클래스
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    private String algorithmType;       // 알고리즘 종류

    private List<Process> processes;    // 프로세스 리스트

    private Integer timeSlice;          // 타임슬라이스 (시간할당량)

}
