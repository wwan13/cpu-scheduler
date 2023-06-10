package com.wwan13.cpuscheduler.processes;

import lombok.*;

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
