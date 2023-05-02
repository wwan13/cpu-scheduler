package com.wwan13.cpuscheduler.Processes;

import lombok.*;

import java.util.List;

/**
 * ResponseDto Class
 * 스케줄링 결과를 Client 로 보내기 위한 클래스
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    
    private String algorithmType;                       // 알고리즘 종료

    private List<Process> processes;                    // 프로세스 리스트

    private List<ScheduledData> scheduledDataList;      // 스케줄링 결과 리스트

    private Double AWT;                                 // 평균대기시간 : Average Wait Time

    private Double ATT;                                 // 평균반환시간 : Average Turn Around Time

    private Double ART;                                 // 평균응답시간 : Average Response Time

}
