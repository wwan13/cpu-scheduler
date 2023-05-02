package com.wwan13.cpuscheduler.Processes;

import lombok.*;

/**
 * ScheduledData Class
 * 스케줄링 결과를 담은 데이터 클래스
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledData {

    private Process process;    // 해당 프로세스

    private Integer startAt;    // 시작 시간

    private Integer endAt;      // 종료 시간

}
